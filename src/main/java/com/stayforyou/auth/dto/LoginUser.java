package com.stayforyou.auth.dto;

import com.stayforyou.core.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class LoginUser {

    private final String username;

    private final String password;

    private final String role;

    public static LoginUser from(Member member) {
        return LoginUser.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .role(member.getRole().name())
                .build();
    }
}
