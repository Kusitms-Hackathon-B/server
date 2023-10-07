package com.example.kusitmshackthon.domain.diet.dto.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class GetGoodDietsResponse {
    List<String> dietsList;
}
