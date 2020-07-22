package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.Manufacturer;
import com.happyride.eservice.entity.model.ManufacturerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ManufacturerModelRepository extends JpaRepository<ManufacturerModel, Long> {

    Optional<ManufacturerModel> findByName(String name);

    Optional<ManufacturerModel> findByNameAndManufacturer(String name, Manufacturer manufacturer);

    Optional<ManufacturerModel> findById(Long id);

    List<ManufacturerModel> findAllByManufacturer(Manufacturer manufacturer);

}
