package com.example.kusitmshackthon.domain.healthlog.repository;

import com.example.kusitmshackthon.domain.healthlog.entity.HealthLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthLogRepository extends JpaRepository<HealthLog, Long> {
}
