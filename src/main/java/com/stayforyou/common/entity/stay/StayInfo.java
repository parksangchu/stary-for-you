package com.stayforyou.common.entity.stay;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StayInfo {

    private Integer price;

    private Integer maxGuests;

    private Integer bedCount;

    private Integer bathroomCount;

    @Builder
    public StayInfo(int price, int maxGuests, int bedCount, int bathroomCount) {
        this.price = price;
        this.maxGuests = maxGuests;
        this.bedCount = bedCount;
        this.bathroomCount = bathroomCount;
    }
}
