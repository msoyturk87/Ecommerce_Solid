package com.cybertek.service;

import com.cybertek.dto.ProductDTO;
import com.cybertek.entity.Currency;
import com.cybertek.entity.Product;
import com.cybertek.entity.SubCategory;
import com.cybertek.entity.Uom;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ProductService {

     Product create(ProductDTO productDTO);
     void update(ProductDTO productDTO) ;
     List<ProductDTO> readAllActive();
     List<ProductDTO> readAll();
    ProductDTO readById(Long id);
     List<ProductDTO> readAllBySubCategory(SubCategory subCategory);
     List<ProductDTO> readAllByUom(Uom foundedUom);
     List<ProductDTO> readAllByCurrency(Currency foundedCurrency);
}
