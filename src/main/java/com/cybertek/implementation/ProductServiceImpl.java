package com.cybertek.implementation;

import com.cybertek.dto.CurrencyDTO;
import com.cybertek.dto.ProductDTO;
import com.cybertek.entity.Currency;
import com.cybertek.entity.Product;
import com.cybertek.enums.Status;
import com.cybertek.entity.*;
import com.cybertek.exception.EcommerceException;
import com.cybertek.mapper.MapperUtil;
import com.cybertek.repository.ProductRepository;
import com.cybertek.service.ProductService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;

    public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil) {
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    @Transactional
    public Product create(ProductDTO productDTO) throws Exception {

        if(productDTO.getName()==null || productDTO.getPrice().compareTo(BigDecimal.ZERO )<0 || productDTO.getQuantity()<=0) {
            throw new Exception("Something went wrong please try again");
        }
        return productRepository.save(mapperUtil.convert(productDTO,new Product()));
    }

    @Override
    @Transactional
    public void update(ProductDTO productDTO) throws Exception {

        if (productDTO.getName() == null || productDTO.getPrice().compareTo(BigDecimal.ZERO) < 0 || productDTO.getQuantity() <= 0) {
            throw new Exception("Something went wrong please try again");
        }
        // name should be unique ??
        Optional<Product> foundedProduct = productRepository.findByName(productDTO.getName());

        if (foundedProduct.isEmpty()) {
            throw new EcommerceException("There is no product to update");
        }
        Product convertedProduct = mapperUtil.convert(productDTO, new Product());
        convertedProduct.setId(foundedProduct.get().getId());

        productRepository.save(convertedProduct);

    }

    @Override
    public List<ProductDTO> readAllActive() {
        List<Product> list = productRepository.findAllByStatus(Status.ACTIVE);// alternate or it is good
        return list.stream().map(obj -> {return mapperUtil.convert(obj,new ProductDTO());}).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> readAll() {
        List<Product> list = productRepository.findAll();// alternate or it is good
        return list.stream().map(obj -> {return mapperUtil.convert(obj,new ProductDTO());}).collect(Collectors.toList());    }

    @Override
    public ProductDTO readById(Long id) throws EcommerceException {
        Product foundedProduct = productRepository.findById(id).orElseThrow(() -> new EcommerceException("This product not found"));

        return mapperUtil.convert(foundedProduct,new ProductDTO());
    }

    @Override
    public List<Product> readAllBySubCategory(SubCategory subCategory) {
        return productRepository.findAllBySubCategoryId(subCategory.getId());

    }
    @Override
    public List<Product> readAllByUom(Uom uom) {
        return productRepository.findByUom(uom);}

    @Override
    public List<Product> readAllByCurrency(Currency currency) {
        return productRepository.findByCurrency(currency);
          }

}
