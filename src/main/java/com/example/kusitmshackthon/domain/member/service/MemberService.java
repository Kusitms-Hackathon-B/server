package com.example.kusitmshackthon.domain.member.service;

import com.example.kusitmshackthon.domain.member.dto.response.MemberAuthResponseDto;
import com.example.kusitmshackthon.domain.member.entity.Member;
import com.example.kusitmshackthon.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberAuthResponseDto signUp(String token, String email, String nickname, int age) {
        Member craeatedMember = Member.createMember(nickname, age, email);
        saveMember(craeatedMember);
        return MemberAuthResponseDto.of(craeatedMember);
    }

    private void saveMember(Member member) {
        memberRepository.save(member);
    }

    private void validateDuplicateMember(String email){

    }
}