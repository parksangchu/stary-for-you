package com.stayforyou.common.entity.stay;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Location {

    private String roadAddress;

    private String lotAddress;

    private Double latitude;

    private Double longitude;

    @Builder
    public Location(String roadAddress, String lotAddress, double latitude, double longitude) {
        this.roadAddress = roadAddress;
        this.lotAddress = lotAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
