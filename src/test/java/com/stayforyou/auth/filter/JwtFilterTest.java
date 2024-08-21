package com.stayforyou.auth.filter;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stayforyou.auth.dto.CustomUserDetails;
import com.stayforyou.auth.util.JwtUtil;
import io.jsonwebtoken.Jwts.SIG;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

class JwtFilterTest {

    private JwtUtil jwtUtil;
    private JwtFilter jwtFilter;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        String secret = "thisIsAVeryLongSecretKeyForTestingPurposesOnly";
        jwtUtil = new JwtUtil(secret, 3600000L);
        ObjectMapper objectMapper = new ObjectMapper();
        jwtFilter = new JwtFilter(jwtUtil, objectMapper);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = new MockFilterChain();
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("유효한 토큰을 지니고 접근시 유저 정보가 Authentication 객체에 저장된다.")
    void doFilterInternal_WithValidToken() throws ServletException, IOException {
        // Given
        String username = "testuser";
        String role = "ROLE_USER";
        String token = jwtUtil.generateToken(username, role);
        request.addHeader("Authorization", "Bearer " + token);

        // When
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then
        UserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        assertThat(userDetails.getUsername()).isEqualTo(username);
        assertThat(userDetails.getAuthorities().iterator().next().getAuthority()).isEqualTo(role);
    }

    @Test
    @DisplayName("Authorization 헤더의 값이 없다면 Authentication 객체는 존재하지 않는다.")
    void doFilterInternal_WithNoAuthorizationHeader() throws ServletException, IOException {
        // When
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("Authorization 헤더의 값이 올바르지 않으면 Authentication 객체는 존재하지 않는다.")
    void doFilterInternal_WithInvalidAuthorizationHeader()
            throws ServletException, IOException {
        // Given
        request.addHeader("Authorization", "Invalid");

        // When
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("만료된 토큰을 지니고 접근하면 401예외가 발생한다.")
    void doFilterInternal_WithExpiredToken() throws ServletException, IOException {
        // Given
        JwtUtil shortLivedJwtUtil = new JwtUtil(SIG.HS512.key().toString(), 1L); // 1ms 만료 시간
        String token = shortLivedJwtUtil.generateToken("testuser", "ROLE_USER");
        try {
            Thread.sleep(100); // 토큰 만료 대기
        } catch (InterruptedException ignored) {
        }
        request.addHeader("Authorization", "Bearer " + token);

        // When
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertThat(response.getStatus()).isEqualTo(401);
        assertThat(response.getContentAsString()).contains("유효하지 않은 토큰입니다.");
    }
}