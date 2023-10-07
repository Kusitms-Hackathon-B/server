package com.example.kusitmshackthon.domain.diet.service;

import com.example.kusitmshackthon.domain.diet.dto.response.GetDietResponse;
import com.example.kusitmshackthon.domain.diet.dto.response.ImageUploadResponse;
import com.example.kusitmshackthon.domain.diet.entity.Diet;
import com.example.kusitmshackthon.domain.diet.repository.DietRepository;
import com.example.kusitmshackthon.domain.food.entity.Food;
import com.example.kusitmshackthon.domain.food.repository.FoodRepository;
import com.example.kusitmshackthon.domain.foodinfo.FoodInfoRepository;
import com.example.kusitmshackthon.domain.foodinfo.entity.FoodInfo;
import com.example.kusitmshackthon.domain.healthlog.entity.HealthLog;
import com.example.kusitmshackthon.domain.healthlog.repository.HealthLogRepository;
import com.example.kusitmshackthon.domain.healthlog.standard.StandardHealthLog;
import com.example.kusitmshackthon.domain.member.entity.Member;
import com.example.kusitmshackthon.domain.member.repository.MemberRepository;
import com.example.kusitmshackthon.exception.notfound.DietNotFoundException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ImageUploadResponse analysUserDiet(MultipartFile image, Long userId) {
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
        return new ImageUploadResponse(diet.getId());
    }

    public GetDietResponse getDietDetails(Long dietId) {
        Diet diet = dietRepository.findById(dietId)
                .orElseThrow(DietNotFoundException::new);

        List<FoodInfo> foodInfoList = diet.getFoodList()
                .stream()
                .map(
                        food -> foodInfoRepository.findByName(food.getName())
                                .orElseThrow(FoodInfoNotFoundException::new)
                ).toList();

        StandardHealthLog standardHealthLog = new StandardHealthLog();

        Float totalDiffProtein = 0F;
        Float totalDiffCalcium = 0F;
        Float totalDiffSodium = 0F;
        Float totalDiffFe = 0F;
        Float totalDiffZinc = 0F;

        Float totalAccuProtein = 0F;
        Float totalAccuCalcium = 0F;
        Float totalAccuSodium = 0F;
        Float totalAccuFe = 0F;
        Float totalAccuZinc = 0F;

        for (FoodInfo foodInfo : foodInfoList) {
            Float protein = foodInfo.getProtein();
            Float calcium = foodInfo.getCalcium();
            Float sodium = foodInfo.getSodium();
            Float fe = foodInfo.getFe();
            Float zinc = foodInfo.getZinc();

            Float stdProtein = standardHealthLog.getProtein();
            Float stdCalcium = standardHealthLog.getCalcium();
            Float stdSodium = standardHealthLog.getSodium();
            Float stdFe = standardHealthLog.getFe();
            Float stdZinc = standardHealthLog.getZinc();

            totalDiffProtein += stdProtein - protein;
            totalDiffCalcium += stdCalcium - calcium;
            totalDiffSodium += stdSodium - sodium;
            totalDiffFe += stdFe - fe;
            totalDiffZinc += stdZinc - zinc;

            totalAccuProtein += protein;
            totalAccuCalcium += calcium;
            totalAccuSodium += sodium;
            totalAccuFe += fe;
            totalAccuZinc += zinc;
        }

        Map<String, Float> diffHm = new HashMap<>();
        Map<String, Float> accuHm = new HashMap<>();
        diffHm.put("단백질", totalDiffProtein);
        diffHm.put("칼슘", totalDiffCalcium);
        diffHm.put("나트륨", totalDiffSodium);
        diffHm.put("철분", totalDiffFe);
        diffHm.put("인", totalDiffZinc);

        accuHm.put("단백질", totalAccuProtein);
        accuHm.put("칼슘", totalAccuCalcium);
        accuHm.put("나트륨", totalAccuSodium);
        accuHm.put("철분", totalAccuFe);
        accuHm.put("인", totalAccuZinc);

        List<GetDietResponse.NutrientInfo> lackList = new ArrayList<>();
        List<GetDietResponse.NutrientInfo> enoughList = new ArrayList<>();
        for (Map.Entry<String, Float> entry : diffHm.entrySet()) {
            String name = entry.getKey();
            Float diff = entry.getValue();

            if (diff >= 0) {
                enoughList.add(GetDietResponse.NutrientInfo.of(name, accuHm.get(name),  diff));
            } else {
                lackList.add(GetDietResponse.NutrientInfo.of(name, accuHm.get(name),  diff));
            }
        }
        return GetDietResponse.of(lackList, enoughList);
    }

}
