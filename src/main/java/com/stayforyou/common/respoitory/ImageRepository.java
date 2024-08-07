package com.stayforyou.common.respoitory;

import com.stayforyou.common.entity.stay.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
