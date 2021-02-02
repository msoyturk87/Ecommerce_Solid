package com.cybertek.service;

import com.cybertek.dto.CategoryDTO;
import com.cybertek.dto.SubCategoryDTO;
import com.cybertek.entity.Category;
import com.cybertek.entity.SubCategory;

import java.util.List;

public interface SubCategoryService {


     SubCategory create(SubCategoryDTO subCategoryDTO);
     void update(SubCategoryDTO subCategoryDTO);
     List<SubCategoryDTO> readAll();
     SubCategoryDTO readById(Integer id);
     void delete(Integer id);
     List<SubCategory> readAllByCategory(Category category);

}
