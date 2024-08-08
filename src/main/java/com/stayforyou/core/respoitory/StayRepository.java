package com.stayforyou.core.respoitory;

import com.stayforyou.core.entity.stay.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StayRepository extends JpaRepository<Stay, Long> {
}
