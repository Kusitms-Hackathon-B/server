package com.example.kusitmshackthon.domain.member.auth.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakao-feign-client", url = "https://kapi.kakao.com/v1/user/access_token_info")
public interface KakaoFeignClient {
    @GetMapping
    KakaoAccessTokenInfo getKakaoAccessTokenInfo(@RequestHeader("Authorization") String accessToken);
}
