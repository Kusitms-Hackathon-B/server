package com.example.kusitmshackthon.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionContext implements ExceptionContext {
    // MEMBER 관련 예외
    NOT_FOUND_MEMBER("존재하지 않는 회원을 조회할 수 없습니다.", 1000),

    // FOOD 관련 예외
    NOT_FOUND_FOOD("존재하지 않는 음식을 조회할 수 없습니다.", 2000),
    BAD_REQUEST_FOOD("음식 이름이 유효하지 않아, 조회할 수 없습니다.", 2001),

    // FOODINFO 관련 예외
    NOT_FOUND_FOOD_INFO("해당 음식에 대한 영양 정보가 존재하지 않아, 조회할 수 없습니다.", 3000),
    NOT_FOUND_HEALTH_LOG("회원에 관한 영양 정보가 없습니다.", 4000),

    // S3 관련 예외
    FILE_UPLOAD_FAILED("S3 파일 업로드에 실패하였습니다.", 5000),
    FILE_SAVE_FAILED("S3 파일 업로드에 실패하였습니다.", 5001),
    FILE_DOWNLOAD_FAILED("S3 파일 업로드에 실패하였습니다.", 5002),
    FILE_INVALID_REQUEST("S3 파일 형식이 유효하지 않습니다.", 5003),

    // Fcm 메세지 전송 실패 예외
    FCM_MESSAGING_FAILED("Fcm 메세지 전송에 싫패하였습니다.", 6000),

    KAKAO_UNAUTHORIZED("Kakao access token이 올바르지 않습니다", 7000),

    NOT_FOUND_DIET("해당 식단에 관한 정보가 없습니다.", 8000),
    ;
    private final String message;
    private final int code;
}