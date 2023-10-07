package com.example.kusitmshackthon.domain.member.service;

import com.example.kusitmshackthon.domain.fcm.entity.FcmToken;
import com.example.kusitmshackthon.domain.fcm.repository.FCMRepository;
import com.example.kusitmshackthon.domain.healthlog.entity.HealthLog;
import com.example.kusitmshackthon.domain.healthlog.repository.HealthLogRepository;
import com.example.kusitmshackthon.domain.healthlog.standard.StandardHealthLog;
import com.example.kusitmshackthon.domain.member.dto.request.SignInRequest;
import com.example.kusitmshackthon.domain.member.dto.request.SignUpRequest;
import com.example.kusitmshackthon.domain.member.dto.response.GetPreviousDietInfoResponse;
import com.example.kusitmshackthon.domain.member.dto.response.MainPageResponse;
import com.example.kusitmshackthon.domain.member.dto.response.MemberAuthResponseDto;
import com.example.kusitmshackthon.domain.member.entity.Member;
import com.example.kusitmshackthon.domain.member.entity.QMember;
import com.example.kusitmshackthon.domain.member.repository.MemberRepository;
import com.example.kusitmshackthon.exception.badrequest.DuplicateMemberException;
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

import static com.example.kusitmshackthon.domain.foodinfo.entity.QFoodInfo.foodInfo;
import static com.example.kusitmshackthon.domain.healthlog.entity.QHealthLog.healthLog;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final HealthLogRepository healthLogRepository;
    private final FCMRepository fcmRepository;
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

        orignHashMap.put("단백질", recentlyHealthLog.getProtein());
        orignHashMap.put("칼숨", recentlyHealthLog.getCalcium());
        orignHashMap.put("나트륨", recentlyHealthLog.getSodium());
        orignHashMap.put("철분", recentlyHealthLog.getFe());
        orignHashMap.put("아연", recentlyHealthLog.getZinc());

        if (recentlyHealthLog.getProtein() != 0) {
            diffHashMap.put("단백질", recentlyHealthLog.getProtein() - standardHealthLog.getProtein());
        }
        if (recentlyHealthLog.getCalcium() != 0) {
            diffHashMap.put("칼숨", recentlyHealthLog.getCalcium() - standardHealthLog.getCalcium());
        }
        if (recentlyHealthLog.getSodium() != 0) {
            diffHashMap.put("나트륨", recentlyHealthLog.getSodium() - standardHealthLog.getSodium());
        }
        if (recentlyHealthLog.getFe() != 0) {
            diffHashMap.put("철분", recentlyHealthLog.getFe() - standardHealthLog.getFe());
        }
        if (recentlyHealthLog.getZinc() != 0) {
            diffHashMap.put("아연", recentlyHealthLog.getZinc() - standardHealthLog.getZinc());
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
        // 이제 추천 식단을 찾는다.

        MainPageResponse.NutrientInfo mostLackNutrient = nutrientInfoList.get(0);
        String mostLackNutrientName = kor2eng(mostLackNutrient.getName());

        String goodDiet;
        if (mostLackNutrientName.equals("protein")) {
            goodDiet = queryFactory.select(foodInfo.name)
                    .from(foodInfo)
                    .orderBy(foodInfo.protein.desc())
                    .limit(1)
                    .fetchOne();
        }
        if (mostLackNutrientName.equals("calcium")) {
            goodDiet = queryFactory.select(foodInfo.name)
                    .from(foodInfo)
                    .orderBy(foodInfo.protein.desc())
                    .limit(1)
                    .fetchOne();
        }
        if (mostLackNutrientName.equals("sodium")) {
            goodDiet = queryFactory.select(foodInfo.name)
                    .from(foodInfo)
                    .orderBy(foodInfo.protein.desc())
                    .limit(1)
                    .fetchOne();
        }
        if (mostLackNutrientName.equals("fe")) {
            goodDiet = queryFactory.select(foodInfo.name)
                    .from(foodInfo)
                    .orderBy(foodInfo.protein.desc())
                    .limit(1)
                    .fetchOne();
        } else {
            goodDiet = queryFactory.select(foodInfo.name)
                    .from(foodInfo)
                    .orderBy(foodInfo.protein.desc())
                    .limit(1)
                    .fetchOne();
        }

        return MainPageResponse.of(nutrientInfoList, goodDiet);
    }

    public String kor2eng(String name) {
        if (name.equals("단백질")) {
            return "protein";
        }
        if (name.equals("칼숨")) {
            return "calcium";
        }
        if (name.equals("나트륨")) {
            return "sodium";
        }
        if (name.equals("철분")) {
            return "fe";
        }
        if (name.equals("아연")) {
            return "zinc";
        }
        return "fe"; // default
    }

    @Transactional
    public MemberAuthResponseDto signIn(SignInRequest request) {
        String email = request.getEmail();
        String fcmToken = request.getFcmToken();
        Member member = getMemberWithEmail(email);
        patchFCMtoken(member, fcmToken);
        return MemberAuthResponseDto.of(member);
    }

    @Transactional
    public MemberAuthResponseDto signUp(SignUpRequest request) {
        String email = request.getEmail();
        String nickname = request.getNickName();
        int age = request.getAge();
        String fcmToken = request.getFcmToken();
        Member craeatedMember = Member.createMember(nickname, age, email);
        validateDuplicateMember(email);
        saveMember(craeatedMember);
        patchFCMtoken(craeatedMember, fcmToken);
        return MemberAuthResponseDto.of(craeatedMember);
    }

    public void patchFCMtoken(Member member, String fcmToken) {
        FcmToken token = FcmToken.of(fcmToken);
        token.setMember(member);
        fcmRepository.save(token);
    }

    private Member getMemberWithEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }

    private void saveMember(Member member) {
        memberRepository.save(member);
    }

    private void validateDuplicateMember(String email) {
        if (memberRepository.existsMemberByEmail(email)) {
            throw new DuplicateMemberException();
        }
    }



    public GetPreviousDietInfoResponse getPreviousDietInfo(Long userId) {
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
        // 해당 회원의 베스트 영양 정보 get 하기 (탄 단 지 당 콜 열량)

        Map<String, Float> orignHashMap = new HashMap<>();

        orignHashMap.put("칼숨", recentlyHealthLog.getCalcium());
        orignHashMap.put("나트륨", recentlyHealthLog.getSodium());
        orignHashMap.put("철분", recentlyHealthLog.getFe());
        orignHashMap.put("아연", recentlyHealthLog.getZinc());


        Float carbohydrate = recentlyHealthLog.getCarbohydrate(); // 탄수화물
        Float protein = recentlyHealthLog.getProtein(); // 단백질
        Float fat = recentlyHealthLog.getFat();// 지방
        Float sugar = recentlyHealthLog.getSugar();// 설탕
        Float cholesterol = recentlyHealthLog.getCholesterol();// 콜레스테롤
        Float energy = recentlyHealthLog.getEnergy();// 열량

        if(energy < 1000){
            energy += 1000;
        }

        return GetPreviousDietInfoResponse.builder()
                .carbohydrate(carbohydrate*2)
                .protein(protein*2)
                .fat(fat*2)
                .sugar(sugar*2)
                .cholesterol(cholesterol*2)
                .energy(energy)
                .build();
    }
}