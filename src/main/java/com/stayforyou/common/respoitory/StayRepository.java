package com.stayforyou.common.respoitory;

import com.stayforyou.common.entity.stay.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StayRepository extends JpaRepository<Stay, Long> {
}
