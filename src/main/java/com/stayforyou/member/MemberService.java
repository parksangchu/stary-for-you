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

    public Member register(String email, String password, String name, String phone) {

        validateDuplicateEmail(email);
        validateDuplicateName(name);

        String encodedPassword = passwordEncoder.encode(password);

        Member member = Member.builder()
                .email(email)
                .password(encodedPassword)
                .name(name)
                .phone(phone)
                .role(ROLE_USER)
                .build();

        memberRepository.save(member);

        return member;
    }

    @Transactional(readOnly = true)
    public Member findByName(String name) {
        return memberRepository.findByNameOrThrow(name);
    }

    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new BadRequestException("이미 존재하는 이메일입니다.");
        }
    }

    private void validateDuplicateName(String name) {
        if (memberRepository.existsByName(name)) {
            throw new BadRequestException("이미 존재하는 이름입니다.");
        }
    }
}
