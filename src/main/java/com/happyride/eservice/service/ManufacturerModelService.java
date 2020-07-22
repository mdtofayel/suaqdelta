package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.Manufacturer;
import com.happyride.eservice.entity.model.ManufacturerModel;
import com.happyride.eservice.repository.ManufacturerModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerModelService {

    @Autowired
    private ManufacturerModelRepository manufacturerRepository;

    public ManufacturerModel saveManufacturerModel(ManufacturerModel manufacturerModel) {

        return manufacturerRepository.save(manufacturerModel);
    }

    public Optional<ManufacturerModel> findByName(String name) {
        return manufacturerRepository.findByName(name);
    }

    public Optional<ManufacturerModel> findByNameAndManufacturerId(String name, Manufacturer manufacturer) {
        return manufacturerRepository.findByNameAndManufacturer(name, manufacturer);
    }

    public List<ManufacturerModel> findAll() {
        return manufacturerRepository.findAll();
    }

    public Optional<ManufacturerModel> findById(Long id) {
        return manufacturerRepository.findById(id);
    }

    public List<ManufacturerModel> findAllByManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.findAllByManufacturer(manufacturer);
    }
}
