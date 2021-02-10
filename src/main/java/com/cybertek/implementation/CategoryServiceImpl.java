package com.cybertek.implementation;


import com.cybertek.dto.CategoryDTO;
import com.cybertek.entity.Category;
import com.cybertek.entity.SubCategory;
import com.cybertek.exception.EcommerceException;
import com.cybertek.mapper.MapperUtil;
import com.cybertek.repository.CategoryRepository;
import com.cybertek.service.CategoryService;
import com.cybertek.service.SubCategoryService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final MapperUtil mapperUtil;
    private final CategoryRepository categoryRepository;
    private final SubCategoryService subCategoryService;

    public CategoryServiceImpl(MapperUtil mapperUtil, CategoryRepository categoryRepository, SubCategoryService subCategoryService) {
        this.mapperUtil = mapperUtil;
        this.categoryRepository = categoryRepository;
        this.subCategoryService = subCategoryService;
    }

    @Override
    @Transactional
    public Category create(CategoryDTO categoryDTO) throws EcommerceException {
        // name is unique
        Category convertedCategory = mapperUtil.convert(categoryDTO, new Category());

        Optional<Category> foundedCategory = categoryRepository.findByName(convertedCategory.getName());

        if(foundedCategory.isPresent())
            throw new EcommerceException("Category Already exist");
        return categoryRepository.save(convertedCategory);
    }

    @Override
    @Transactional
    public void update(CategoryDTO categoryDTO) throws EcommerceException {
        Category convertedCategory = mapperUtil.convert(categoryDTO, new Category());

        Optional<Category> foundedCategory = categoryRepository.findByName(convertedCategory.getName());

        if (foundedCategory.isEmpty()) {
            throw new EcommerceException("There is no category");
        }
        convertedCategory.setId(foundedCategory.get().getId());

        categoryRepository.save(convertedCategory);
    }

    @Override
    public List<CategoryDTO> readAll() {

        List<Category> list = categoryRepository.findAll(Sort.by("name"));
        return list.stream().map(obj -> mapperUtil.convert(obj,new CategoryDTO())).collect(Collectors.toList());

    }

    @Override
    public CategoryDTO readById(Integer id) throws EcommerceException {

        Category foundedCategory = categoryRepository.findById(id)
                            .orElseThrow(()->new EcommerceException("This category does not exist"));
        return mapperUtil.convert(foundedCategory,new CategoryDTO());
    }

    @Override
    @Transactional
    public void deleteById(Integer id) throws EcommerceException {

        Category foundedCategory = categoryRepository.findById(id).orElseThrow(() -> new EcommerceException("category doesn't exist"));

        List<SubCategory> subCategories = subCategoryService.readAllByCategory(foundedCategory);

        if( subCategories.size()>0 ) {
            throw new EcommerceException("This category can not be deleted");
        }

        foundedCategory.setName(foundedCategory.getName()+"-"+foundedCategory.getId());
        foundedCategory.setIsDeleted(true);
        categoryRepository.save(foundedCategory);
    }
}

