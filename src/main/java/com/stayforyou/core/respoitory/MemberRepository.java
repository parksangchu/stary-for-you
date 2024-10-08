package com.stayforyou.core.respoitory;

import com.stayforyou.common.exception.NotFoundException;
import com.stayforyou.core.entity.member.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member findByEmailOrThrow(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new NotFoundException("이메일과 일치하는 사용자 정보가 없습니다."));
    }

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("""
            SELECT m from Member m 
            join m.socialAccounts sa
            WHERE sa.provider = :provider AND sa.providerId = :providerId
            """)
    Optional<Member> findBySocialAccount(String provider, String providerId);
}
