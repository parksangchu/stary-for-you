package com.stayforyou.auth.service;

import com.stayforyou.core.entity.member.Member;
import com.stayforyou.core.entity.member.Role;
import com.stayforyou.core.entity.member.SocialAccount;
import com.stayforyou.core.respoitory.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SocialMemberService {

    private final MemberRepository memberRepository;

    public Member getMember(String provider, String providerId, String email, String nickname) {
        return memberRepository.findBySocialAccount(provider, providerId) // 소셜 정보와 일치하는 멤버 존재 여부 확인
                .orElseGet(() -> linkOrRegister(provider, providerId, email, nickname));
    }

    private Member linkOrRegister(String provider, String providerId, String email, String nickname) {
        // 소셜 정보로 멤버를 찾지 못한다면 이메일 정보와 일치하는 멤버를 찾아 소셜 정보 등록
        SocialAccount socialAccount = SocialAccount.builder()
                .provider(provider)
                .providerId(providerId)
                .build();

        return memberRepository.findByEmail(email)
                .map(member -> {
                    member.linkSocialAccount(socialAccount);
                    return member;
                })
                .orElseGet(() -> register(socialAccount, email, nickname)); // 완전 새로운 유저라면 회원가입
    }

    private Member register(SocialAccount socialAccount, String email, String nickname) {
        Member member = Member.builder()
                .email(email)
                .nickname(nickname)
                .role(Role.ROLE_USER)
                .build();

        member.linkSocialAccount(socialAccount);
        memberRepository.save(member);

        return member;
    }
}
