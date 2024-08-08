package com.stayforyou.core.entity.stay;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class StaySchedule {

    private Integer checkInTime;

    private Integer checkOutTime;

    private Integer minStayDuration; // 최소 숙박 가능 기간 (일수)

    private Integer maxStayDuration; // 최대 숙박 가능 기간 (일수)

    private Integer availableMonthsInAdvance; // 게스트가 예약할 수 있는 최대한 먼 날짜(개월)

    private Integer maxDaysBeforeBooking; //예약을 받는 시점과 체크인 사이에 시간


    @Builder
    public StaySchedule(Integer checkInTime, Integer checkOutTime, Integer minStayDuration, Integer maxStayDuration,
                        Integer availableMonthsInAdvance, Integer maxDaysBeforeBooking) {
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.minStayDuration = minStayDuration;
        this.maxStayDuration = maxStayDuration;
        this.availableMonthsInAdvance = availableMonthsInAdvance;
        this.maxDaysBeforeBooking = maxDaysBeforeBooking;
    }
}
