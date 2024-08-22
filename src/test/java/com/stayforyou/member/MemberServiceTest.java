package com.stayforyou.member;

import static com.stayforyou.core.entity.member.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;

import com.stayforyou.core.entity.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    void register() {
        String username = "test";
        String password = "1234";
        String email = "test@email.com";
        String nickname = "testnick";

        Member member = memberService.register(username, password, email, nickname);

        assertThat(member.getId()).isNotNull();
        assertThat(member.getRole()).isEqualTo(ROLE_USER);
        assertThat(member.getPassword()).isNotEqualTo(password);
    }
}