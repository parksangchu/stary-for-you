package com.stayforyou.auth.filter;

import com.stayforyou.auth.dto.CustomUserDetails;
import com.stayforyou.auth.dto.LoginUser;
import com.stayforyou.auth.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization == null || !authorization.startsWith("Bearer ")) {

            doFilter(request, response, filterChain);
            return;
        }
        
        String token = authorization.substring(7);

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        LoginUser loginUser = LoginUser.builder()
                .username(username)
                .role(role)
                .build();

        UserDetails userDetails = new CustomUserDetails(loginUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        doFilter(request, response, filterChain);
    }
}
