package com.cybertek.service;

import com.cybertek.dto.CategoryDTO;
import com.cybertek.dto.SubCategoryDTO;
import com.cybertek.entity.Category;
import com.cybertek.entity.SubCategory;
import com.cybertek.exception.EcommerceException;

import java.util.List;

public interface SubCategoryService {



     SubCategory create(SubCategoryDTO subCategoryDTO) throws EcommerceException;

     void update(SubCategoryDTO subCategoryDTO) throws EcommerceException;
     List<SubCategoryDTO> readAll();
     SubCategoryDTO readById(Integer id) throws EcommerceException;
     void delete(Integer id) throws EcommerceException;
     List<SubCategory> readAllByCategory(Category category);

}
