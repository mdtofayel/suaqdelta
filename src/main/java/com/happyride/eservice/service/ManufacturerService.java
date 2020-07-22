package com.happyride.eservice.service;


import com.happyride.eservice.entity.model.Manufacturer;
import com.happyride.eservice.entity.model.SubCategory;
import com.happyride.eservice.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public Optional<Manufacturer> findManufacturerByName(String name) {
        return manufacturerRepository.findByName(name);
    }

    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public List<Manufacturer> findAllManufacturer() {
        return manufacturerRepository.findAll();
    }

    public Optional<Manufacturer> findManufacturerById(Long id) {

        return manufacturerRepository.findById(id);
    }

    public List<Manufacturer> findAllBySubCategory(List<SubCategory> subCategories) {
        return manufacturerRepository.findAllBySubCategory(subCategories);
    }
}
