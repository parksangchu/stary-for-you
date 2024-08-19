package com.stayforyou.member.dto;

import com.stayforyou.core.entity.member.Member;
import com.stayforyou.core.entity.member.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class MemberDetailResponse {

    private final Long id;

    private final String email;

    private final String name;

    private final String phone;

    private final Role role;
    
    public static MemberDetailResponse from(Member member) {
        return MemberDetailResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhone())
                .role(member.getRole())
                .build();
    }
}
