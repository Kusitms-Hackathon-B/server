package com.example.kusitmshackthon.domain.member.service;

import com.example.kusitmshackthon.domain.healthlog.entity.HealthLog;
import com.example.kusitmshackthon.domain.healthlog.repository.HealthLogRepository;
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
import java.util.List;

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
        List<MainPageResponse.NutrientInfo> nutrientInfoList = new ArrayList<>();
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

        System.out.println("recentlyHealthLog = " + recentlyHealthLog);

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