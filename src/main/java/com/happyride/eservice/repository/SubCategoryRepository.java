package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.Category;
import com.happyride.eservice.entity.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;


public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    Optional<SubCategory> findByName(String name);

    List<SubCategory> getSubCategoriesByCategory(Category category);
}
