package com.example.kusitmshackthon.domain.fcm.dto.request;

import com.example.kusitmshackthon.domain.fcm.entity.FcmToken;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FcmNotificationRequestDto {
    private List<FcmToken> targetTokenList;
    private String title;
    private String body;

    @Builder
    public FcmNotificationRequestDto(List<FcmToken> targetTokenList, String title, String body) {
        this.targetTokenList = targetTokenList;
        this.title = title;
        this.body = body;
    }

    public static FcmNotificationRequestDto of(List<FcmToken> targetTokenList, String title, String body) {
        return FcmNotificationRequestDto.builder()
                .targetTokenList(targetTokenList)
                .title(title)
                .body(body)
                .build();
    }
}

