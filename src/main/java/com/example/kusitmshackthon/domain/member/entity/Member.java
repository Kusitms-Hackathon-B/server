package com.example.kusitmshackthon.domain.member.entity;

import com.example.kusitmshackthon.domain.diet.entity.Diet;
import com.example.kusitmshackthon.domain.fcm.entity.FcmToken;
import com.example.kusitmshackthon.domain.healthlog.entity.HealthLog;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column
    private String name;

    @Column
    private Integer age;

    @OneToMany(mappedBy =  "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diet> dietList = new ArrayList<>();

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FcmToken fcmToken;

    @OneToMany(mappedBy =  "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HealthLog> healthLog = new ArrayList<>();
}
