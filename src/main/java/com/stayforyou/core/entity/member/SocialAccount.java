package com.stayforyou.core.entity.member;

import com.stayforyou.core.entity.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class SocialAccount extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String provider;

    private String providerId;

    @Builder
    public SocialAccount(String provider, String providerId) {
        this.provider = provider;
        this.providerId = providerId;
    }

    public void linkMember(Member member) {
        this.member = member;
    }
}
