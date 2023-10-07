package com.example.kusitmshackthon.domain.member.auth.kakao;

import com.example.kusitmshackthon.exception.badrequest.KakaoUnauthorizedException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KakaoOAuthProvider {
    private final KakaoFeignClient kakaoFeignClient;

    public String getKakaoPlatformId(String accessToken) {
        KakaoAccessToken kakaoAccessToken = KakaoAccessToken.createKakaoAccessToken(accessToken);
        String accessTokenWithTokenType = kakaoAccessToken.getAccessTokenWithTokenType();
        KakaoAccessTokenInfo kakaoAccessTokenInfo = getKakaoAccessTokenInfo(accessTokenWithTokenType);
        return String.valueOf(kakaoAccessTokenInfo.getId());
    }

    private KakaoAccessTokenInfo getKakaoAccessTokenInfo(String accessTokenWithTokenType) {
        try {
            return kakaoFeignClient.getKakaoAccessTokenInfo(accessTokenWithTokenType);
        } catch (FeignException e) {
            log.error("Feign Exception: ", e);
            throw new KakaoUnauthorizedException();
        }
    }
}
