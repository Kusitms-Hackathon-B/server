package com.example.kusitmshackthon.domain.healthlog.entity;

import com.example.kusitmshackthon.domain.foodinfo.entity.FoodInfo;
import com.example.kusitmshackthon.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "HEALTH_LOG")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HealthLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "health_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    private Member member;

    LocalDateTime intakeAt; // 식사(섭취) 시간
    LocalDate intakeDate;

    // 영양 정보
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

    public HealthLog(FoodInfo foodInfo) {
        this.weight = foodInfo.getWeight();
        this.energy = foodInfo.getEnergy();
        this.carbohydrate = foodInfo.getCarbohydrate();
        this.sugar = foodInfo.getSugar();
        this.fat = foodInfo.getFat();
        this.protein = foodInfo.getProtein();
        this.calcium = foodInfo.getCalcium();
        this.phos = foodInfo.getPhos();
        this.sodium = foodInfo.getSodium();
        this.potassium = foodInfo.getPotassium();
        this.magnesium = foodInfo.getMagnesium();
        this.fe = foodInfo.getFe();
        this.zinc = foodInfo.getZinc();
        this.calcium = foodInfo.getCalcium();
        this.cholesterol = foodInfo.getCholesterol();
        this.intakeAt = LocalDateTime.now(); // 생성 시점으로 저장
        this.intakeDate = LocalDate.now();
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public static HealthLog of(FoodInfo foodInfo) {
        return new HealthLog(foodInfo);
    }
}
