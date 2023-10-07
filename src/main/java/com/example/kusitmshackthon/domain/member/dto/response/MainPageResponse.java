package com.example.kusitmshackthon.domain.member.dto.response;


import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class MainPageResponse {

    List<NutrientInfo> nutrientInfoList;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @ToString
    public static class NutrientInfo {
        private String name;
        private Float amount;
        private Float diff;

        public static MainPageResponse.NutrientInfo of (String name, Float amount, Float diff){
            return new MainPageResponse.NutrientInfo(name, amount, diff);
        }
    }

    public static MainPageResponse of(List<NutrientInfo> nutrientInfoList) {
        return new MainPageResponse(nutrientInfoList);
    }
}
