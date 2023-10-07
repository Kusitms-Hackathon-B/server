package com.example.kusitmshackthon.domain.member.repository;

import com.example.kusitmshackthon.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
