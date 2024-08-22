package com.stayforyou.core.entity.member;

import com.stayforyou.core.entity.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseModel {

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    @Column(unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String username, String password, String email, String nickname, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
    }
}
