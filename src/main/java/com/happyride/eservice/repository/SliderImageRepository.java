package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.SliderImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface SliderImageRepository extends JpaRepository<SliderImage, Long> {
}
