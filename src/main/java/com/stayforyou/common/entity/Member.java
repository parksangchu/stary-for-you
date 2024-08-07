package com.stayforyou.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseModel {

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String name;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;
}
