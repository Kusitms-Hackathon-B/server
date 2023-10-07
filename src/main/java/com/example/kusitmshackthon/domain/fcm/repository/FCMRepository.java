package com.example.kusitmshackthon.domain.fcm.repository;

import com.example.kusitmshackthon.domain.fcm.entity.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FCMRepository extends JpaRepository<FcmToken, Long> {
}
