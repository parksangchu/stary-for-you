package com.stayforyou.auth.handler;

import com.stayforyou.auth.dto.CustomOAuth2User;
import com.stayforyou.auth.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class CustomOAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final String targetRedirectUrl;

    private final JwtUtil jwtUtil;

    public CustomOAuthSuccessHandler(@Value("${spring.security.oauth2.redirect-uri}") String targetRedirectUrl,
                                     JwtUtil jwtUtil) {
        this.targetRedirectUrl = targetRedirectUrl;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String token = jwtUtil.generateToken(customOAuth2User.getName(), customOAuth2User.getRole());

        String redirectUrl = UriComponentsBuilder.fromUriString(targetRedirectUrl)
                .queryParam(HttpHeaders.AUTHORIZATION, token)
                .build()
                .toUriString();

        response.sendRedirect(redirectUrl);
    }
}
