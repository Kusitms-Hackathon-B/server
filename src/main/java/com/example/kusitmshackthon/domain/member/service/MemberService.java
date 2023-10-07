package com.example.kusitmshackthon.domain.member.service;

import com.example.kusitmshackthon.domain.healthlog.entity.HealthLog;
import com.example.kusitmshackthon.domain.healthlog.repository.HealthLogRepository;
import com.example.kusitmshackthon.domain.healthlog.standard.StandardHealthLog;
import com.example.kusitmshackthon.domain.member.dto.response.MainPageResponse;
import com.example.kusitmshackthon.domain.member.dto.response.MemberAuthResponseDto;
import com.example.kusitmshackthon.domain.member.entity.Member;
import com.example.kusitmshackthon.domain.member.entity.QMember;
import com.example.kusitmshackthon.domain.member.repository.MemberRepository;
import com.example.kusitmshackthon.exception.notfound.MemberNotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.kusitmshackthon.domain.healthlog.entity.QHealthLog.healthLog;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final HealthLogRepository healthLogRepository;
    private JPAQueryFactory queryFactory;
    @PersistenceContext
    private EntityManager entityManager;

    public MainPageResponse getMainPage(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(MemberNotFoundException::new);
        LocalDate nowDate = LocalDate.now();

        queryFactory = new JPAQueryFactory(entityManager);

        List<HealthLog> healthLogs
                = healthLogRepository.findByMemberIdAndIntakeDateEquals(userId, nowDate);
        // 해당 회원의 식사
        QMember qMember = QMember.member;

        // 가장 가까운 시간에 섭취한 식단 영양 정보
        HealthLog recentlyHealthLog = queryFactory.selectFrom(healthLog)
                .join(healthLog.member, qMember)
                .where(healthLog.member.id.eq(qMember.id)
                        .and(healthLog.intakeDate.eq(nowDate)))
                .orderBy(healthLog.intakeAt.desc())
                .limit(1)
                .fetchOne();
        // 해당 회원의 베스트 영양 정보 get 하기
        StandardHealthLog standardHealthLog = new StandardHealthLog();

        Map<String, Float> diffHashMap = new HashMap<>();
        Map<String, Float> orignHashMap = new HashMap<>();

        orignHashMap.put("protein", recentlyHealthLog.getProtein());
        orignHashMap.put("calcium", recentlyHealthLog.getCalcium());
        orignHashMap.put("sodium", recentlyHealthLog.getSodium());
        orignHashMap.put("fe", recentlyHealthLog.getFe());
        orignHashMap.put("zinc", recentlyHealthLog.getZinc());

        if (recentlyHealthLog.getProtein() != 0) {
            diffHashMap.put("protein", recentlyHealthLog.getProtein() - standardHealthLog.getProtein());
        }
        if (recentlyHealthLog.getCalcium() != 0) {
            diffHashMap.put("calcium", recentlyHealthLog.getCalcium() - standardHealthLog.getCalcium());
        }
        if (recentlyHealthLog.getSodium() != 0) {
            diffHashMap.put("sodium", recentlyHealthLog.getSodium() - standardHealthLog.getSodium());
        }
        if (recentlyHealthLog.getFe() != 0) {
            diffHashMap.put("fe", recentlyHealthLog.getFe() - standardHealthLog.getFe());
        }
        if (recentlyHealthLog.getZinc() != 0) {
            diffHashMap.put("zinc", recentlyHealthLog.getZinc() - standardHealthLog.getZinc());
        }


        List<Map.Entry<String, Float>> collect = diffHashMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .toList();

        List<MainPageResponse.NutrientInfo> nutrientInfoList = new ArrayList<>();
        int cnt = 0;
        for (Map.Entry<String, Float> entry : collect) {
            String key = entry.getKey();
            Float diff = entry.getValue();
            if (Math.abs(diff) <= 200) {
                cnt++;
                nutrientInfoList.add(MainPageResponse.NutrientInfo.of(
                        key, orignHashMap.get(key), diff
                ));
                if (cnt == 3) {
                    break;
                }
            }
        }

        return MainPageResponse.of(nutrientInfoList);
    }

    @Transactional
    public MemberAuthResponseDto signUp(String token, String email, String nickname, int age) {
        Member craeatedMember = Member.createMember(nickname, age, email);
        saveMember(craeatedMember);
        return MemberAuthResponseDto.of(craeatedMember);
    }

    @Transactional
    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    private void validateDuplicateMember(String email) {

    }
}