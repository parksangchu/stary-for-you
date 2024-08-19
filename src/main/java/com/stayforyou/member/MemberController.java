package com.stayforyou.member;

import com.stayforyou.core.entity.member.Member;
import com.stayforyou.member.dto.MemberCreateRequest;
import com.stayforyou.member.dto.MemberCreateResponse;
import com.stayforyou.member.dto.MemberDetailResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public MemberCreateResponse createMember(@Valid @RequestBody MemberCreateRequest request) {

        Member member = memberService.register(request.getEmail(), request.getPassword(), request.getName(),
                request.getPhone());

        return MemberCreateResponse.from(member);
    }

    @GetMapping("/me")
    public MemberDetailResponse getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        Member member = memberService.findByName(userDetails.getUsername());

        return MemberDetailResponse.from(member);
    }
}
