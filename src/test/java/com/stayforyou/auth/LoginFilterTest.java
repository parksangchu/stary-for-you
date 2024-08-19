package com.stayforyou.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stayforyou.auth.dto.LoginTryRequest;
import com.stayforyou.core.entity.member.Member;
import com.stayforyou.core.entity.member.Role;
import com.stayforyou.core.respoitory.MemberRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LoginFilterTest {

    private static final String TEST_EMAIL = "test@email.com";
    public static final String TEST_PASSWORD = "test123";
    public static final String LOGIN_ERROR = "아이디나 비밀번호가 일치하지 않습니다.";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Member member = new Member(TEST_EMAIL, passwordEncoder.encode(TEST_PASSWORD), "test", "111-1111-1111",
                Role.ROLE_USER);

        memberRepository.save(member);
    }

    @Test
    @DisplayName("이메일와 비밀번호가 모두 일치하면 accessToken을 발급하여 Authorization 헤더의 값으로 전달한다.")
    void attemptAuthentication_success() throws Exception {
        String json = objectMapper.writeValueAsString(new LoginTryRequest(TEST_EMAIL, TEST_PASSWORD));

        mockMvc.perform(post("/api/auth")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(header().string("Authorization", Matchers.matchesPattern("Bearer .+")));
    }

    @Test
    @DisplayName("잘못된 비밀번호를 입력하면 401예외가 발생한다.")
    void attemptAuthentication_wrongPassword() throws Exception {
        String json = objectMapper.writeValueAsString(new LoginTryRequest(TEST_EMAIL, "wrongpassword"));

        mockMvc.perform(post("/api/auth")
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(LOGIN_ERROR));

    }

    @Test
    @DisplayName("존재하지 않는 이메일을 입력하면 401예외가 발생한다.")
    void attemptAuthentication_nonExistentUser() throws Exception {
        String json = objectMapper.writeValueAsString(new LoginTryRequest("nonexistent@email.com", TEST_PASSWORD));

        mockMvc.perform(post("/api/auth")
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(LOGIN_ERROR));
    }
}