package com.example.kusitmshackthon.domain.fcm.repository;

import com.example.kusitmshackthon.domain.fcm.entity.FcmToken;
import com.example.kusitmshackthon.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FcmRepository extends JpaRepository<FcmToken, Long> {
    FcmToken findByMemeber(Member member);

    Optional<FcmToken> findByUserId(Long userId);

    Optional<FcmToken> findByFcmToken(String fcmToken);

    List<FcmToken> findByUserParticipationsMeetingId(Long meetingId);
}
