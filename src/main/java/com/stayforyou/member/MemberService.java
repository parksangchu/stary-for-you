package com.stayforyou.member;

import static com.stayforyou.core.entity.member.Role.ROLE_USER;

import com.stayforyou.common.exception.BadRequestException;
import com.stayforyou.core.entity.member.Member;
import com.stayforyou.core.respoitory.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public Member register(String email, String password, String nickname) {

        validateDuplicateEmail(email);

        String encodedPassword = passwordEncoder.encode(password);

        Member member = Member.builder()
                .email(email)
                .password(encodedPassword)
                .nickname(nickname)
                .role(ROLE_USER)
                .build();

        memberRepository.save(member);

        return member;
    }

    @Transactional(readOnly = true)
    public Member findByEmailOrThrow(String email) {
        return memberRepository.findByEmailOrThrow(email);
    }

    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new BadRequestException("이미 사용중인 이메일입니다.");
        }
    }
}
