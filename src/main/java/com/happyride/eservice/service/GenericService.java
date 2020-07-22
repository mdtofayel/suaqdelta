package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.Generic;
import com.happyride.eservice.model.Condition;
import com.happyride.eservice.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class GenericService {
    @Autowired
    private GenericRepository genericRepository;

    /*public List<Generic> findByManufacturer(Manufacturer manufacturer) {
        return genericRepository.findByManufacturer(manufacturer);
    }*/

    public Optional<Generic> findById(Long id) {
        return genericRepository.findById(id);
    }

    public List<Generic> findByCondition(Condition condition) {
        return genericRepository.findByCondition(condition);
    }

    public void saveGeneric(Generic generic) {
        genericRepository.save(generic);
    }

    public void delete(Generic generic) {
        genericRepository.delete(generic);
    }
}
