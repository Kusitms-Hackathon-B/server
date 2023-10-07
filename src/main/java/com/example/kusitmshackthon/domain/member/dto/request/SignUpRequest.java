package com.example.kusitmshackthon.domain.member.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@ToString
public class SignUpRequest {
    String email;
    String nickName;
    int age;
    String fcmToken;
}
