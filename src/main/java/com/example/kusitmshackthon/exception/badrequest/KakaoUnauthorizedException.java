package com.example.kusitmshackthon.exception.badrequest;

import static com.example.kusitmshackthon.exception.CustomExceptionContext.KAKAO_UNAUTHORIZED;

public class KakaoUnauthorizedException extends BadRequestException {
    public KakaoUnauthorizedException() {
        super(KAKAO_UNAUTHORIZED);
    }
}