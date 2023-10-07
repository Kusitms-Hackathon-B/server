package com.example.kusitmshackthon.domain.diet.service;

import com.example.kusitmshackthon.domain.diet.repository.DietRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DietService {
    private final DietRepository dietRepository;
}
