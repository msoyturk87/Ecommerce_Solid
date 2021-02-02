package com.cybertek.repository;

import com.cybertek.entity.Category;
import com.cybertek.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Integer> {

    Optional<SubCategory>  findByNameAndCategoryId(String name, Integer id);
    List<SubCategory> findAllByCategory(Category category);

    }


