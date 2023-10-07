package com.example.kusitmshackthon.support.dto.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class FlaskResponse {
    List<String> foodNameList;
}
