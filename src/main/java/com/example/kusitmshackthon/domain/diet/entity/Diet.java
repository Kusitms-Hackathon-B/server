package com.example.kusitmshackthon.domain.diet.entity;


import com.example.kusitmshackthon.domain.diet.DietType;
import com.example.kusitmshackthon.domain.food.entity.Food;
import com.example.kusitmshackthon.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "DIET")
public class Diet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diet_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private DietType dietType; // 아(B), 점(M), 저(D)

    String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    private Member member;


    //@OneToMany(mappedBy =  "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy =  "diet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Food> foodList = new ArrayList<>();

}
