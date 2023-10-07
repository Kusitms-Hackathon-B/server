package com.example.kusitmshackthon.domain.food.repository;

import com.example.kusitmshackthon.domain.food.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
