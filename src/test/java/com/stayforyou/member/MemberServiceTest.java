package com.stayforyou.member;

import static com.stayforyou.core.entity.member.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;

import com.stayforyou.core.entity.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    void register() {
        String email = "test@email.com";
        String password = "1234";
        String name = "test";
        String phone = "111-1111-1111";

        Member member = memberService.register(email, password, name, phone);

        assertThat(member.getId()).isNotNull();
        assertThat(member.getRole()).isEqualTo(ROLE_USER);
        assertThat(member.getPassword()).isNotEqualTo(password);
    }
}