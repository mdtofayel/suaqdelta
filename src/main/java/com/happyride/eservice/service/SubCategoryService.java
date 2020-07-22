package com.happyride.eservice.service;


import com.happyride.eservice.entity.model.Category;
import com.happyride.eservice.entity.model.SubCategory;
import com.happyride.eservice.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    @Autowired
    public SubCategoryService(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    public void saveSubCategories(SubCategory subCategory) {
        subCategoryRepository.save(subCategory);
    }

    public Optional<SubCategory> getSubCategoryById(Long id) {
        return subCategoryRepository.findById(id);
    }

    public Optional<SubCategory> findByName(String name) {
        return subCategoryRepository.findByName(name);
    }

    public List<SubCategory> findAll() {
        return subCategoryRepository.findAll();
    }

    public  List<SubCategory> findSubCategoriesByCategoryId(Category category){
        return subCategoryRepository.getSubCategoriesByCategory(category);
    }
}
