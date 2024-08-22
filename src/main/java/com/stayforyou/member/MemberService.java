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

    public Member register(String username, String password, String email, String nickname) {

        validateDuplicateUsername(username);
        validateDuplicateNickname(nickname);

        String encodedPassword = passwordEncoder.encode(password);

        Member member = Member.builder()
                .username(username)
                .password(encodedPassword)
                .email(email)
                .nickname(nickname)
                .role(ROLE_USER)
                .build();

        memberRepository.save(member);

        return member;
    }

    @Transactional(readOnly = true)
    public Member findByUsername(String username) {
        return memberRepository.findByUsernameOrThrow(username);
    }

    private void validateDuplicateUsername(String username) {
        if (memberRepository.existsByUsername(username)) {
            throw new BadRequestException("이미 존재하는 사용자 이름입니다.");
        }
    }

    private void validateDuplicateNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new BadRequestException("이미 존재하는 닉네임입니다.");
        }
    }
}
