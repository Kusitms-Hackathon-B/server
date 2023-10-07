package com.example.kusitmshackthon.domain.diet.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class ImageUploadRequest {
    MultipartFile image;
}
