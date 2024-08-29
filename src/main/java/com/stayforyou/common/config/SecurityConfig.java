package com.stayforyou.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stayforyou.auth.filter.JwtFilter;
import com.stayforyou.auth.filter.LoginFilter;
import com.stayforyou.auth.handler.CustomOAuthSuccessHandler;
import com.stayforyou.auth.service.CustomOAuth2UserService;
import com.stayforyou.auth.util.JwtUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    private final JwtUtil jwtUtil;

    private final ObjectMapper objectMapper;

    private final CustomOAuth2UserService customOAuth2UserService;

    private final CustomOAuthSuccessHandler customOAuthSuccessHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(getCorsConfigurationSource()));

        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth", "/health").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/members").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(getAuthenticationEntryPoint()));

        http
                .oauth2Login(oauth2 -> oauth2

                        .userInfoEndpoint(
                                userInfoEndpointConfig -> userInfoEndpointConfig
                                        .userService(customOAuth2UserService))
                        .successHandler(customOAuthSuccessHandler));
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, objectMapper,
                        "/api/auth"), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtFilter(jwtUtil, objectMapper), UsernamePasswordAuthenticationFilter.class);

        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private CorsConfigurationSource getCorsConfigurationSource() {
        return request -> {

            String origin = request.getHeader("Origin");
            CorsConfiguration configuration = new CorsConfiguration();
            if (origin != null) {
                configuration.setAllowedOrigins(List.of(origin));
            }

            configuration.setAllowedMethods(List.of("*"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(List.of("*"));

            configuration.setExposedHeaders(List.of("Authorization"));

            return configuration;
        };
    }

    private AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return (request, response, authException) -> response.sendError(403);
    }
}
