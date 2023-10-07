package com.example.kusitmshackthon.domain.foodinfo;

import com.example.kusitmshackthon.domain.foodinfo.entity.FoodInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodInfoRepository extends JpaRepository<FoodInfo, Long> {
    Optional<FoodInfo> findByName(String name);
}
