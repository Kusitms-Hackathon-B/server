package com.example.kusitmshackthon.domain.diet.controller;

import com.example.kusitmshackthon.domain.diet.dto.response.PostDietResponse;
import com.example.kusitmshackthon.domain.diet.service.DietService;
import com.example.kusitmshackthon.support.dto.response.FlaskResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
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
            식단 이미지와 유저 ID 를 보내면 영양소 정보를 분석하여 전달합니다.
            (현재는 더미 데이터)
            """)
    @PostMapping("/{userId}")
    public ResponseEntity<PostDietResponse> postDiet(
            @RequestParam("image") final MultipartFile image,
            @PathVariable("userId") Long userId) {

        PostDietResponse response = dietService.analysUserDiet(image, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }


    // test
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
}
