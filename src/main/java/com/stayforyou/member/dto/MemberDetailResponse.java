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

    private final String nickname;

    private final Role role;

    public static MemberDetailResponse from(Member member) {
        return MemberDetailResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .role(member.getRole())
                .build();
    }
}
