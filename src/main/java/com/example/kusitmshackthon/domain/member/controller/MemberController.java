package com.example.kusitmshackthon.domain.member.controller;


import com.example.kusitmshackthon.domain.member.dto.response.MainPageResponse;
import com.example.kusitmshackthon.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/{userId}")
    public ResponseEntity<MainPageResponse> getMainPage(@PathVariable(name = "userId") Long userId) {
        MainPageResponse response = memberService.getMainPage(userId);
        return ResponseEntity.ok(response);
    }
}
