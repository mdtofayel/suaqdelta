package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.Category;
import com.happyride.eservice.entity.model.PostType;
import com.happyride.eservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> categoryById(Long Id) {
        return categoryRepository.findById(Id);
    }

    public void saveCategories(Category category) {
        categoryRepository.save(category);
    }

    public Optional<Category> findCategoryByName(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }

    public List<Category> findCategoryListByName(String categoryName) {
        return categoryRepository.findListByName(categoryName);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public List<Category> findByPostType(PostType postType) {
        return categoryRepository.findByPostType(postType);
    }

    public Optional<Category>  findCategoryByNameAndPost(String name, PostType postType){
       return categoryRepository.findByNameIgnoreCaseAndPostType(name, postType);
    }
}
