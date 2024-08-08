package com.stayforyou.core.respoitory;

import com.stayforyou.core.entity.stay.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
