package com.cybertek.service;

import com.cybertek.dto.CategoryDTO;
import com.cybertek.entity.Category;
import com.cybertek.exception.EcommerceException;


import java.util.List;

public interface CategoryService {



    Category create(CategoryDTO categoryDTO) throws EcommerceException;

    void update(CategoryDTO categoryDTO) throws EcommerceException;

    List<CategoryDTO> readAll();

    CategoryDTO readById(Integer id) throws EcommerceException;

    void deleteById(Integer id) throws EcommerceException;

}
