package com.cybertek.service;

import com.cybertek.dto.ProductDTO;
import com.cybertek.entity.Currency;
import com.cybertek.entity.Product;
import com.cybertek.entity.SubCategory;
import com.cybertek.entity.Uom;
import com.cybertek.exception.EcommerceException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ProductService {

     Product create(ProductDTO productDTO) throws Exception;

     void update(ProductDTO productDTO) throws Exception;
     List<ProductDTO> readAllActive();
     List<ProductDTO> readAll();
     ProductDTO readById(Long id) throws EcommerceException;
     List<Product> readAllBySubCategory(SubCategory subCategory);
     List<Product> readAllByUom(Uom foundedUom);
     List<Product> readAllByCurrency(Currency foundedCurrency);
}
