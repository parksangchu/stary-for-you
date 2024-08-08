package com.stayforyou.core.entity.stay;

import com.stayforyou.core.entity.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Amenity extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stay_id")
    private Stay stay;

    @Enumerated
    private AmenityType amenityType;

    @Builder
    public Amenity(Stay stay, AmenityType amenityType) {
        this.stay = stay;
        this.amenityType = amenityType;
    }
}
