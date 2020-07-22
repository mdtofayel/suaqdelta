package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.SliderImage;
import com.happyride.eservice.repository.SliderImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class SliderImageService {
    @Autowired
    private SliderImageRepository sliderImageRepository;


    public Optional<SliderImage> getSliderImageById(Long id) {
        return sliderImageRepository.findById(id);
    }

    public List<SliderImage> sliderImageList() {
        return sliderImageRepository.findAll();
    }

    public void save(SliderImage sliderImage) {
        sliderImageRepository.save(sliderImage);
    }

    public void delete(SliderImage sliderImage) {
        sliderImageRepository.delete(sliderImage);
    }

    public void deleteAll() {
        sliderImageRepository.deleteAll();
    }
}
