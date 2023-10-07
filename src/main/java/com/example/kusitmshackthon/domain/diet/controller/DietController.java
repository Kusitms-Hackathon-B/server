package com.example.kusitmshackthon.domain.diet.controller;

import com.example.kusitmshackthon.domain.diet.dto.response.GetDietResponse;
import com.example.kusitmshackthon.domain.diet.dto.response.GetGoodDietsResponse;
import com.example.kusitmshackthon.domain.diet.dto.response.ImageUploadResponse;
import com.example.kusitmshackthon.domain.diet.service.DietService;
import com.example.kusitmshackthon.support.dto.response.FlaskResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/diet")
@RestController
public class DietController {
    private final DietService dietService;

    @Operation(summary = "식단 등록/기록(with image)", description = """
            식단 이미지와 유저 ID 를 보내면 영양소 정보를 분석하고,
                        
            식단 ID 를 반환합니다.
                        
            식단 이미지는 RequestParam MultipartFile 형식으로, 이름은 반드시 image 로 넘겨주세요!
                        
            해당 식단의 분석 정보 api 에서 식단 ID 를 넘겨서 분석 정보를 받을 수 있습니다.
            """)
    @PostMapping("/{userId}")
    public ResponseEntity<ImageUploadResponse> postDiet(
            @RequestParam("image") final MultipartFile image,
            @PathVariable("userId") Long userId) {
        ImageUploadResponse response = dietService.analysUserDiet(image, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(summary = "해당 식단의 분석 정보를 제공하는 api 입니다.", description =
            """
                    식단 ID 를 path variable 으로 보내주면
                                
                    부족한 영양소 리스트,
                                
                    충분한 영양소 리스트를 제공합니다.
                                
                    name 은 영양소 이름이고,
                    amount 는 섭취량,
                    diff 는 권장 섭취량 - amount 입니다.
                    """)
    @GetMapping("/{dietId}")
    public ResponseEntity<GetDietResponse> getDietInfo(
            @PathVariable("dietId") Long dietId) {
        GetDietResponse response = dietService.getDietDetails(dietId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    // test
    @Operation(summary = "Flask API")
    @PostMapping("/test")
    public ResponseEntity<FlaskResponse> flaskDummyApi(
            @RequestParam("image") final MultipartFile image) {
        System.out.println("image = " + image);
        List<String> foodNameList = new ArrayList<>();
        foodNameList.add("찹쌀떡");
        foodNameList.add("양파장아찌");
        FlaskResponse response = new FlaskResponse(foodNameList);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(summary = "추천 식단 3개 Get")
    @GetMapping("/suggest")
    public ResponseEntity<GetGoodDietsResponse> getGoodDiet() {
        GetGoodDietsResponse response = dietService.getGoodDiets();
        return ResponseEntity.ok(response);
    }

}
