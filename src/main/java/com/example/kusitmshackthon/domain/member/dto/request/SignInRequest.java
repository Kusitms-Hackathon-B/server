package com.example.kusitmshackthon.domain.member.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@ToString
public class SignInRequest {
    String email;
    String fcmToken;
}
