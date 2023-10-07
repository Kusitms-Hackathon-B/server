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
        private Integer plus;
        private Integer minus;
    }

    public static MainPageResponse of(List<NutrientInfo> nutrientInfoList) {
        return new MainPageResponse(nutrientInfoList);
    }
}
