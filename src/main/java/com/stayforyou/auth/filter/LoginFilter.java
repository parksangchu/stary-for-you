package com.stayforyou.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stayforyou.auth.dto.LoginTryRequest;
import com.stayforyou.auth.util.JwtUtil;
import com.stayforyou.common.exception.ErrorResultUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jWtUtil;

    private final ObjectMapper objectMapper;

    public LoginFilter(AuthenticationManager authenticationManager,
                       JwtUtil jWtUtil, ObjectMapper objectMapper, String url) {
        this.authenticationManager = authenticationManager;
        this.jWtUtil = jWtUtil;
        this.objectMapper = objectMapper;
        setFilterProcessesUrl(url);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        LoginTryRequest loginTryRequest = getLoginTryRequest(request);

        String email = loginTryRequest.getEmail();
        String password = loginTryRequest.getPassword();

        UsernamePasswordAuthenticationToken authTryToken = new UsernamePasswordAuthenticationToken(email, password);

        return authenticationManager.authenticate(authTryToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) {

        String username = authResult.getName();
        String role = getRole(authResult);

        String accessToken = jWtUtil.generateToken(username, role);
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {

        ErrorResultUtil.sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "아이디나 비밀번호가 일치하지 않습니다.", objectMapper);
    }

    private LoginTryRequest getLoginTryRequest(HttpServletRequest request) {
        try {
            return objectMapper.readValue(request.getInputStream(), LoginTryRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getRole(Authentication authResult) {
        return authResult.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse(null);
    }
}
