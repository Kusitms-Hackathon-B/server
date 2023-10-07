package com.example.kusitmshackthon.domain.member.entity;

import com.example.kusitmshackthon.domain.diet.entity.Diet;
import com.example.kusitmshackthon.domain.fcm.entity.FcmToken;
import com.example.kusitmshackthon.domain.healthlog.entity.HealthLog;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column
    private String name;

    @Column
    private Integer age;
    @Column
    private String email;

    @OneToMany(mappedBy =  "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diet> dietList = new ArrayList<>();

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FcmToken fcmToken;

    public static Member createMember(String userName, int age, String email){
        return Member.builder()
                .name(userName)
                .age(age)
                .email(email)
                .build();
    }

    @OneToMany(mappedBy =  "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HealthLog> healthLog = new ArrayList<>();
}
