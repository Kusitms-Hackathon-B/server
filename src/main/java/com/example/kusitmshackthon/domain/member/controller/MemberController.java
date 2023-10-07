package com.example.kusitmshackthon.domain.member.controller;


import com.example.kusitmshackthon.domain.member.dto.response.MainPageResponse;
import com.example.kusitmshackthon.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "가장 최근에 섭취한 식단에서, 부족한 영양소 top 3 조회", description =
                    """
                    name 은 영양소 이름이고, amount 는 섭취량, diff: 는 권장 섭취량 - amount 입니다!
                    
                    부족한 순으로 정렬해서 나갑니다..
                    """)
    @GetMapping("/{userId}")
    public ResponseEntity<MainPageResponse> getMainPage(@PathVariable(name = "userId") Long userId) {
        MainPageResponse response = memberService.getMainPage(userId);
        return ResponseEntity.ok(response);
    }
}
