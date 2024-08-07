package com.stayforyou.common.respoitory;

import com.stayforyou.common.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
