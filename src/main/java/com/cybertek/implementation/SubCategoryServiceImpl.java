package com.cybertek.implementation;

import com.cybertek.model.Category;
import com.cybertek.model.Product;
import com.cybertek.model.SubCategory;
import com.cybertek.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryServiceImpl {
    private final SubCategoryRepository subCategoryRepository;
    private final ProductServiceImpl productService;

    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, ProductServiceImpl productService) {
        this.subCategoryRepository = subCategoryRepository;
        this.productService = productService;
    }

    public SubCategory create(SubCategory subCategory) throws Exception {

        Optional<SubCategory> foundSubCategory =
                subCategoryRepository.findByNameAndCategoryId(subCategory.getName(), subCategory.getCategory().getId());

        if(foundSubCategory.isPresent()){
            throw new Exception("Sub Category already exists ");
        }
        return subCategoryRepository.save(subCategory);
    }

    public void update(SubCategory subCategory) throws Exception {


        SubCategory foundedSubCategory = subCategoryRepository.findByNameAndCategoryId(subCategory.getName(), subCategory.getCategory().getId())
                .orElseThrow(() -> new Exception("This category does not exist"));

        subCategory.setId(foundedSubCategory.getId());
        subCategoryRepository.save(subCategory);
    }

     public List<SubCategory> readAll(){

       return subCategoryRepository.findAll();
     }

    public SubCategory readById(Integer id){

        return subCategoryRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) throws Exception {
        SubCategory foundedSubCategory = subCategoryRepository.findById(id).orElseThrow(() -> new Exception("This subCategory does not exist "));


        List<Product> products = productService.readAllBySubCategory(foundedSubCategory);

        // TODO verify this link

        if( products.size()>0 ) {
            throw new Exception("This subCategory can not be deleted");
        }

        foundedSubCategory.setName(foundedSubCategory.getName()+"-"+foundedSubCategory.getId());
        foundedSubCategory.setIsDeleted(true);
        subCategoryRepository.save(foundedSubCategory);
    }

    public List<SubCategory> readAllByCategory(Category category){

        return subCategoryRepository.findAllByCategory(category);
    }


}
