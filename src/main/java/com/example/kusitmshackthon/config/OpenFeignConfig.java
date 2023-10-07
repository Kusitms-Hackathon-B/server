package com.example.kusitmshackthon.config;

import com.example.kusitmshackthon.KusitmsHackthonApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.AbstractFormWriter;
import org.springframework.cloud.openfeign.support.JsonFormWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableFeignClients(basePackageClasses = KusitmsHackthonApplication.class)
public class OpenFeignConfig {
    @Bean
    public AbstractFormWriter jsonFormWriter() {
        return new JsonFormWriter();
    }
    @Bean
    public Encoder multipartFormEncoder() {
        return new SpringFormEncoder
                (new SpringEncoder(() -> new HttpMessageConverters(new RestTemplate().getMessageConverters())));
    }
}
