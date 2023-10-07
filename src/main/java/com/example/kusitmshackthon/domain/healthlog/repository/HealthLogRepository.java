package com.example.kusitmshackthon.domain.healthlog.repository;

import com.example.kusitmshackthon.domain.healthlog.entity.HealthLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HealthLogRepository extends JpaRepository<HealthLog, Long> {
    List<HealthLog> findByMemberId(Long memberId);
    List<HealthLog> findByMemberIdAndIntakeDateEquals(Long memberId, LocalDate intakeDate);
}
