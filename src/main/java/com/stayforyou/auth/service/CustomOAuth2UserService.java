package com.stayforyou.auth.service;

import com.stayforyou.auth.dto.CustomOAuth2User;
import com.stayforyou.auth.dto.GoogleResponse;
import com.stayforyou.auth.dto.LoginUser;
import com.stayforyou.auth.dto.OAuth2Response;
import com.stayforyou.core.entity.member.Member;
import com.stayforyou.core.entity.member.Role;
import com.stayforyou.core.respoitory.MemberRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2Response oAuth2Response = new GoogleResponse(attributes);

        Member member = registerOrGet(oAuth2Response.getEmail(), oAuth2Response.getName());

        LoginUser loginUser = LoginUser.from(member);

        return new CustomOAuth2User(loginUser);
    }

    private Member registerOrGet(String email, String nickname) {
        return memberRepository.findByEmail(email)
                .orElseGet(() -> register(email, nickname));
    }

    private Member register(String email, String nickname) {
        Member member = Member.builder()
                .email(email)
                .nickname(nickname)
                .role(Role.ROLE_USER)
                .build();

        return memberRepository.save(member);
    }
}
