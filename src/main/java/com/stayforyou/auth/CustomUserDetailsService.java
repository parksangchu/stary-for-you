package com.stayforyou.auth;

import com.stayforyou.core.entity.member.Member;
import com.stayforyou.core.respoitory.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmailOrThrow(username);

        LoginUser loginUser = LoginUser.from(member);

        return new CustomUserDetails(loginUser);
    }
}
