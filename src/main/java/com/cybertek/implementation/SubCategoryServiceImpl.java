package com.cybertek.implementation;

import com.cybertek.dto.ProductDTO;
import com.cybertek.dto.SubCategoryDTO;
import com.cybertek.entity.Category;
import com.cybertek.entity.Product;
import com.cybertek.entity.SubCategory;
import com.cybertek.exception.EcommerceException;
import com.cybertek.mapper.MapperUtil;
import com.cybertek.repository.SubCategoryRepository;
import com.cybertek.service.ProductService;
import com.cybertek.service.SubCategoryService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final MapperUtil mapperUtil;
    private final ProductService productService;

    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, MapperUtil mapperUtil, ProductService productService) {

        this.subCategoryRepository = subCategoryRepository;
        this.mapperUtil = mapperUtil;
        this.productService = productService;
    }

    @Override
    @Transactional
    public SubCategory create(SubCategoryDTO subCategoryDTO) throws EcommerceException {

        Optional<SubCategory> foundSubCategory =
                subCategoryRepository.findByNameAndCategoryId(subCategoryDTO.getName(), subCategoryDTO.getCategory().getId());

        if(foundSubCategory.isPresent()){
            throw new EcommerceException("Sub Category already exists ");
        }
        return subCategoryRepository.save(mapperUtil.convert(subCategoryDTO,new SubCategory()));    }

    @Override
    @Transactional
    public void update(SubCategoryDTO subCategoryDTO) throws EcommerceException {

        SubCategory foundedSubCategory = subCategoryRepository.findByNameAndCategoryId(subCategoryDTO.getName(), subCategoryDTO.getCategory().getId())
                .orElseThrow(() -> new EcommerceException("This category does not exist"));
        SubCategory convertSubCategory = mapperUtil.convert(subCategoryDTO, new SubCategory());
        convertSubCategory.setId(foundedSubCategory.getId());
        subCategoryRepository.save(convertSubCategory);
    }

    @Override
    public List<SubCategoryDTO> readAll() {
        List<SubCategory> list = subCategoryRepository.findAll();
        return list.stream().map(obj -> {return mapperUtil.convert(obj,new SubCategoryDTO());}).collect(Collectors.toList());
    }

    @Override
    public SubCategoryDTO readById(Integer id) throws EcommerceException {
        SubCategory foundedSubCategory = subCategoryRepository.findById(id).orElseThrow(() -> new EcommerceException("This subCategory not found"));

        return mapperUtil.convert(foundedSubCategory,new SubCategoryDTO());    }

    @Override
    @Transactional
    public void delete(Integer id) throws EcommerceException {
        SubCategory foundedSubCategory = subCategoryRepository.findById(id).orElseThrow(() -> new EcommerceException("This subCategory does not exist "));


        List<Product> products = productService.readAllBySubCategory(foundedSubCategory);

        // TODO verify this link

        if( products.size()>0 ) {
            throw new EcommerceException("This subCategory can not be deleted");
        }

        foundedSubCategory.setName(foundedSubCategory.getName()+"-"+foundedSubCategory.getId());
        foundedSubCategory.setIsDeleted(true);
        subCategoryRepository.save(foundedSubCategory);
    }

    @Override
    public List<SubCategory> readAllByCategory(Category category) {
        return subCategoryRepository.findAllByCategory(category);
    }










    /*

    public void delete(Integer id) throws Exception {

    }

    public List<SubCategory> readAllByCategory(Category category){

        return subCategoryRepository.findAllByCategory(category);
    }
*/

}
