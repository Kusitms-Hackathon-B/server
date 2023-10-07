package com.example.kusitmshackthon.support;

import com.example.kusitmshackthon.support.dto.response.FlaskResponse;
import feign.Headers;
import feign.form.ContentType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@FeignClient(name = "APIOpenFeign", url = "http://localhost:8080/api/diet/test") // todo: setting
public interface APIOpenFeign {
    @Headers("Content-Type: multipart/form-data")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    FlaskResponse call(@RequestPart(value = "image") MultipartFile image);
}
