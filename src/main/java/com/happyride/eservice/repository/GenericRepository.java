package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.Generic;
import com.happyride.eservice.model.Condition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface GenericRepository extends JpaRepository<Generic, Long> {
    // List<Generic> findByManufacturer(Manufacturer manufacturer);

    List<Generic> findByCondition(Condition condition);
}
