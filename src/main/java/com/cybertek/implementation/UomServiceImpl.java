package com.cybertek.implementation;

import com.cybertek.dto.SubCategoryDTO;
import com.cybertek.dto.UomDTO;
import com.cybertek.entity.Product;
import com.cybertek.entity.SubCategory;
import com.cybertek.entity.Uom;
import com.cybertek.exception.EcommerceException;
import com.cybertek.mapper.MapperUtil;
import com.cybertek.repository.UomRepository;
import com.cybertek.service.UomService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UomServiceImpl implements UomService {

    private final UomRepository uomRepository;
    private final ProductServiceImpl productService;
    private final MapperUtil mapperUtil;

    public UomServiceImpl(UomRepository uomRepository, ProductServiceImpl productService, MapperUtil mapperUtil) {
        this.uomRepository = uomRepository;
        this.productService = productService;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public Uom create(UomDTO uomDTO) throws EcommerceException {
        //name should be unique
        Optional<Uom> foundedUom = uomRepository.findByName(uomDTO.getName());

        if(foundedUom.isPresent()) {
            throw new EcommerceException("This Uom already exist.You can not create! ");
        }


        return uomRepository.save(mapperUtil.convert(uomDTO,new Uom()));  // ask this part
    }

    @Override
    public void update(UomDTO uomDTO) throws EcommerceException {
            Uom foundedUom = uomRepository.findByName(uomDTO.getName()).orElseThrow(()->new EcommerceException("There is no Uom"));

            Uom convertUom = mapperUtil.convert(uomDTO, new Uom());

            convertUom.setId(foundedUom.getId());

            uomRepository.save(convertUom);

    }

    @Override
    public List<UomDTO> readAll() {
        List<Uom> list = uomRepository.findAll();
        return list.stream().map(obj->{ return mapperUtil.convert(obj,new UomDTO());
        }).collect(Collectors.toList());

    }

    @Override
    public UomDTO readById(Integer id) throws EcommerceException {
        Uom foundedUom = uomRepository.findById(id).orElseThrow(() -> new EcommerceException("This subCategory not found"));

        return mapperUtil.convert(foundedUom,new UomDTO());    }

    @Override
    public void deleteById(Integer id) throws EcommerceException {
        Uom foundedUom=uomRepository.findById(id).orElseThrow(()->new EcommerceException("it is already deleted"));
        List<Product> products = productService.readAllByUom(foundedUom);

        if(products.size()>0) {
            throw new EcommerceException("This Uom can not be deleted");
        }
        foundedUom.setName(foundedUom.getName()+"-"+foundedUom.getId());
        foundedUom.setIsDeleted(true);
        uomRepository.save(foundedUom);
    }

}
