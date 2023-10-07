package com.example.kusitmshackthon.domain.diet.dto.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class GetDietResponse {
    List<NutrientInfo> lackList;
    List<NutrientInfo> enoughList;

    public static GetDietResponse of(List<NutrientInfo> lackList, List<NutrientInfo> enoughList) {
        return new GetDietResponse(lackList, enoughList);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @ToString
    public static class NutrientInfo {
        private String name;
        private Float amount;
        private Float diff;

        public static NutrientInfo of(String name, Float amount, Float diff) {
            return new NutrientInfo(name, amount, diff);
        }
    }
}
