package com.stayforyou.core.respoitory;

import com.stayforyou.common.exception.NotFoundException;
import com.stayforyou.core.entity.member.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member findByEmailOrThrow(String email) {
        return findByEmail(email).orElseThrow(() -> new NotFoundException("사용자 이메일과 일치하는 멤버 정보가 존재하지 않습니다."));
    }

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByName(String name);
}
