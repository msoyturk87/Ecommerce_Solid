package com.cybertek.implementation;

import com.cybertek.enums.Status;
import com.cybertek.model.*;
import com.cybertek.repository.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product create(Product product) throws Exception {

        if(product.getName()==null || product.getPrice().compareTo(BigDecimal.ZERO )<0 || product.getQuantity()<=0) {
            throw new Exception("Something went wrong please try again");
        }
        return productRepository.save(product);
    }

    @Transactional  // TODO Add all update - create -delete method for Transactional
    public void update(Product product) throws Exception {

        productRepository.findById(product.getId())
                .orElseThrow(()->new Exception("This product does not exists"));

        if(product.getName()==null || product.getPrice().compareTo(BigDecimal.ZERO )<0 || product.getQuantity()<=0) {
            throw new Exception("Something went wrong please try again");
        }


        productRepository.save(product);
    }

    public List<Product> readAllActive(){

        return productRepository.findAllByStatus(Status.ACTIVE); // alternate or it is good
    }

    public List<Product> readAll(){

        return productRepository.findAll(Sort.by("name"));
    }
    public Product readById(Long id){

        return productRepository.findById(id).orElse(null);
    }

    public List<Product> readAllBySubCategory(SubCategory subCategory){

        return productRepository.findAllBySubCategoryId(subCategory.getId());
    }


    public List<Product> readAllByUom(Uom foundedUom) {
        return productRepository.findByUom(foundedUom);
    }

    public List<Product> readAllByCurrency(Currency foundedCurrency) {

        return productRepository.findByCurrency(foundedCurrency);
    }
}
