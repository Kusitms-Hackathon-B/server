package com.example.kusitmshackthon.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FcmConfig {
    @Bean
    FirebaseApp firebaseApp() throws IOException {
        ClassPathResource resource = new ClassPathResource("firebase/ssang-1a9ab-firebase-adminsdk-7nen2-2c1210a093.json");
        InputStream refreshToken = resource.getInputStream();
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .build();
        return FirebaseApp.initializeApp(options);
    }

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        return FirebaseMessaging.getInstance(firebaseApp());
    }
}
