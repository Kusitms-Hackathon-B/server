package com.example.kusitmshackthon.domain.member.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@ToString
public class GetPreviousDietInfoResponse {
    Float carbohydrate; // 탄수화물
    Float protein; // 단백질
    Float fat;// 지방
    Float sugar;// 설탕
    Float cholesterol;// 콜레스테롤
    Float energy;// 열량
}
