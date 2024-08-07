package com.stayforyou.common.entity.stay;

import com.stayforyou.common.entity.BaseModel;
import com.stayforyou.common.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Stay extends BaseModel {

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private StayType stayType;

    @Embedded
    private StayInfo stayInfo;

    @Embedded
    private Location location;

    @Embedded
    private StaySchedule staySchedule;

    @OneToMany(mappedBy = "stay")
    private List<Amenity> amenities = new ArrayList<>();

    @OneToMany(mappedBy = "stay")
    private List<Image> images = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StayStatus stayStatus;
}
