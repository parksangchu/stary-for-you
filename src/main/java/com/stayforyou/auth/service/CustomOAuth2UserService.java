package com.stayforyou.auth.service;

import com.stayforyou.auth.dto.CustomOAuth2User;
import com.stayforyou.auth.dto.GoogleResponse;
import com.stayforyou.auth.dto.LoginUser;
import com.stayforyou.auth.dto.OAuth2Response;
import com.stayforyou.core.entity.member.Member;
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

    private final SocialMemberService socialMemberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Map<String, Object> attributes = super.loadUser(userRequest).getAttributes();

        OAuth2Response oAuth2Response = new GoogleResponse(attributes);

        Member member = socialMemberService.getMember(oAuth2Response.getProvider(), oAuth2Response.getProviderId(),
                oAuth2Response.getEmail(), oAuth2Response.getName());

        LoginUser loginUser = LoginUser.from(member);

        return new CustomOAuth2User(loginUser);
    }

}
