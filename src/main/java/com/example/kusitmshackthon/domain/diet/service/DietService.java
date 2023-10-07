package com.example.kusitmshackthon.domain.diet.service;

import com.example.kusitmshackthon.domain.diet.dto.response.PostDietResponse;
import com.example.kusitmshackthon.domain.diet.entity.Diet;
import com.example.kusitmshackthon.domain.diet.repository.DietRepository;
import com.example.kusitmshackthon.domain.food.entity.Food;
import com.example.kusitmshackthon.domain.food.repository.FoodRepository;
import com.example.kusitmshackthon.domain.foodinfo.FoodInfoRepository;
import com.example.kusitmshackthon.domain.foodinfo.entity.FoodInfo;
import com.example.kusitmshackthon.domain.healthlog.entity.HealthLog;
import com.example.kusitmshackthon.domain.healthlog.repository.HealthLogRepository;
import com.example.kusitmshackthon.domain.member.entity.Member;
import com.example.kusitmshackthon.domain.member.repository.MemberRepository;
import com.example.kusitmshackthon.exception.notfound.FoodInfoNotFoundException;
import com.example.kusitmshackthon.exception.notfound.MemberNotFoundException;
import com.example.kusitmshackthon.support.APIOpenFeign;
import com.example.kusitmshackthon.support.dto.response.FlaskResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DietService {
    private final DietRepository dietRepository;
    private final APIOpenFeign apiOpenFeign;
    private final MemberRepository memberRepository;
    private final FoodRepository foodRepository;
    private final FoodInfoRepository foodInfoRepository;
    private final HealthLogRepository healthLogRepository;

    @Transactional
    public PostDietResponse analysUserDiet(MultipartFile image, Long userId) {
        // 회원 조회
        Member member = memberRepository.findById(userId)
                .orElseThrow(MemberNotFoundException::new);
        Diet diet = dietRepository.save(Diet.of(member));

        // ai 모델로부터 얻어낸 음식 리스트 get
        FlaskResponse response = apiOpenFeign.call(image);

        List<String> foodNameList = response.getFoodNameList();
        List<Food> foodEntities = new ArrayList<>();

        for (String foodName : foodNameList) {
            Food food = Food.of(foodName);
            foodRepository.save(food);
            foodEntities.add(food);
            food.setDiet(diet); // 연관관계 설정
            FoodInfo foodInfo = foodInfoRepository.findByName(food.getName())
                    .orElseThrow(FoodInfoNotFoundException::new);
            HealthLog healthLog = healthLogRepository.save(HealthLog.of(foodInfo));
            healthLog.setMember(member);
        }
        diet.setFoodList(foodEntities); // 해당 식단에 어떤 음식들이 포함되어 있는지 설정함.
        // create dummy data // todo: remove it

        List<PostDietResponse.NutrientInfo> lackList = new ArrayList<>();
        List<PostDietResponse.NutrientInfo> enoughList = new ArrayList<>();
        lackList.add(new PostDietResponse.NutrientInfo("탄수화물", 110, 0, 20));
        enoughList.add(new PostDietResponse.NutrientInfo("칼슘", 120, 10, 0));
        enoughList.add(new PostDietResponse.NutrientInfo("단백질", 130, 20, 0));
        return new PostDietResponse(lackList, enoughList); // todo
    }
}
