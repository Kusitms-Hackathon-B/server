package com.example.kusitmshackthon.domain.foodinfo.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "FOOD_INFO")
public class FoodInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_info_id")
    private Long id;

    private String name;

    Float weight; // 중량(g)
    Float energy; // 에너지(kcal)
    Float carbohydrate; // 탄수화물(g)
    Float sugar; // 당류
    Float fat; // 지방(g)
    Float protein; // 단백질(g)
    Float calcium; // 칼슘(mg)
    Float phos; // 인(mg)
    Float sodium; // 나트륨(mg)
    Float potassium; //칼륨(mg)
    Float magnesium; //마그네슘(mg)
    Float fe; // 철(mg)
    Float zinc; // 아연(mg)
    Float cholesterol; // 콜레스테롤(mg)
}
