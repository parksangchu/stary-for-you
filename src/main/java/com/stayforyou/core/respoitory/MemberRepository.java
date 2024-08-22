package com.stayforyou.core.respoitory;

import com.stayforyou.common.exception.NotFoundException;
import com.stayforyou.core.entity.member.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member findByUsernameOrThrow(String username) {
        return findByUsername(username).orElseThrow(() -> new NotFoundException("사용자 이름과 일치하는 멤버 정보가 존재하지 않습니다."));
    }

    Optional<Member> findByUsername(String username);

    Optional<Member> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);
}
