package com.stayforyou.member.dto;

import com.stayforyou.core.entity.member.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberCreateResponse {

    private final Long memberId;

    public static MemberCreateResponse from(Member member) {
        return new MemberCreateResponse(member.getId());
    }
}
