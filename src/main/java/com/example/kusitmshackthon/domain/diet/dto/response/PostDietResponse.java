package com.example.kusitmshackthon.domain.diet.dto.response;

import lombok.*;

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
