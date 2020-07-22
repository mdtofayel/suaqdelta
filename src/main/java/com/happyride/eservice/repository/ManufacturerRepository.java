package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.Manufacturer;
import com.happyride.eservice.entity.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    Optional<Manufacturer> findByName(String name);

    List<Manufacturer> findAllBySubCategory(List<SubCategory> subCategories);
}
