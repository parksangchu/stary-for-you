package com.stayforyou.core.respoitory;

import com.stayforyou.core.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
