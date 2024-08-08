package com.stayforyou.member;

import static com.stayforyou.core.entity.member.Role.ROLE_USER;

import com.stayforyou.core.entity.member.Member;
import com.stayforyou.core.respoitory.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public Member register(String email, String password, String name, String phone) {

        String encodedPassword = passwordEncoder.encode(password);

        Member member = Member.builder()
                .email(email)
                .password(encodedPassword)
                .name(name)
                .phone(phone)
                .role(ROLE_USER)
                .build();

        memberRepository.save(member);

        log.info("새로운 유저가 회원가입하였습니다. id = {}", member.getId());

        return member;
    }
}
