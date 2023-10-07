package com.example.kusitmshackthon.domain.member.controller;


import com.example.kusitmshackthon.domain.member.dto.request.SignInRequest;
import com.example.kusitmshackthon.domain.member.dto.request.SignUpRequest;
import com.example.kusitmshackthon.domain.member.dto.response.GetPreviousDietInfoResponse;
import com.example.kusitmshackthon.domain.member.dto.response.MainPageResponse;
import com.example.kusitmshackthon.domain.member.dto.response.MemberAuthResponseDto;
import com.example.kusitmshackthon.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "가장 최근에 섭취한 식단에서, 부족한 영양소 top 3 조회", description =
            """
                    name 은 영양소 이름이고, amount 는 섭취량, diff: 는 권장 섭취량 - amount 입니다!
                                        
                    부족한 순으로 정렬해서 나갑니다.
                                        
                    추천 식단(goodDietName)도 같이 반환하도록 수정했습니다.
                    """)
    @GetMapping("/{userId}")
    public ResponseEntity<MainPageResponse> getMainPage(@PathVariable(name = "userId") Long userId) {
        MainPageResponse response = memberService.getMainPage(userId);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "가장 최근에 섭취한 식단에서, 탄당지, 콜레스테롤, 당류, 총열량 제공", description =
                    """
                    탄수화물, 당류, 지방, 콜레스테롤, 당류, 총열량을 제공합니다.
                    """)
    @GetMapping("/{userId}/detail")
    public ResponseEntity<GetPreviousDietInfoResponse> getPreviousDietInfo(
            @PathVariable(name = "userId") Long userId){
        GetPreviousDietInfoResponse response = memberService.getPreviousDietInfo(userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "카카오 회원가입 API 입니다.", description = """
            email, nickName, age, fcmToken 을 보내주세요.
            """)
    @PostMapping("/singUp")
    public ResponseEntity<MemberAuthResponseDto> signUp(@RequestBody SignUpRequest request) {
        MemberAuthResponseDto responseDto = memberService.signUp(request);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "카카오 로그인 API 입니다.", description =
            """
                    email, fcmToken 을 보내주세요.
                    """)
    @PostMapping("/signIn")
    public ResponseEntity<MemberAuthResponseDto> signIn(
            @RequestBody SignInRequest request) {
        MemberAuthResponseDto responseDto = memberService.signIn(request);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<?> logout(@RequestParam Long userId) {
        memberService.memberDelete(userId);
        return ResponseEntity.ok(null);
    }
}
