package com.stayforyou.common.entity.stay;

import com.stayforyou.common.entity.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Image extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stay_id")
    private Stay stay;

    private String originalName;

    private String storeName;

    private String url;

    @Builder
    public Image(Stay stay, String originalName, String storeName, String url) {
        this.stay = stay;
        this.originalName = originalName;
        this.storeName = storeName;
        this.url = url;
    }
}
