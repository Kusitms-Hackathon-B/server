package com.example.kusitmshackthon.domain.member.repository;

import com.example.kusitmshackthon.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsMemberByEmail(String email);
    Optional<Member> findByEmail(String email);
}
