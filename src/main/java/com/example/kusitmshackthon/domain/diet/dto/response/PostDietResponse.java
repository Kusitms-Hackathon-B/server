package com.example.kusitmshackthon.domain.diet.dto.response;

import com.example.kusitmshackthon.domain.food.entity.Food;
import lombok.*;
import org.apache.catalina.LifecycleState;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class PostDietResponse {
    List<NutrientInfo> lackList;
    List<NutrientInfo> enoughList;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @ToString
    public static class NutrientInfo {
        private String name;
        private Integer amount;
        private Integer plus;
        private Integer minus;
    }
}
