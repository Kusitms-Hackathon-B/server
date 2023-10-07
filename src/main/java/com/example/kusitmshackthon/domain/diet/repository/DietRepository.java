package com.example.kusitmshackthon.domain.diet.repository;

import com.example.kusitmshackthon.domain.diet.entity.Diet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DietRepository extends JpaRepository<Diet, Long> {
}
