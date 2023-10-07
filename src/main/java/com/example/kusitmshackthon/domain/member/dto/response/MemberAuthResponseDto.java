package com.example.kusitmshackthon.domain.member.dto.response;

import com.example.kusitmshackthon.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberAuthResponseDto {
    private Long memberId;
    private int age;
    private String nickname;

    public static MemberAuthResponseDto of(Member member) {
        return MemberAuthResponseDto.builder()
                .memberId(member.getId())
                .age(member.getAge())
                .nickname(member.getName())
                .build();
    }
}
