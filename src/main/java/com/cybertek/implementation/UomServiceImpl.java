package com.cybertek.implementation;

import com.cybertek.model.Product;
import com.cybertek.model.Uom;
import com.cybertek.repository.UomRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UomServiceImpl {

    private final UomRepository uomRepository;
    private final ProductServiceImpl productService;

    public UomServiceImpl(UomRepository uomRepository, ProductServiceImpl productService) {
        this.uomRepository = uomRepository;
        this.productService = productService;
    }

    public Uom create(Uom uom) throws Exception {

        Optional<Uom> foundedUom = uomRepository.findByName(uom.getName());

        if(foundedUom.isPresent()) {
            throw new Exception("This Uom already exist.You can not create! ");
        }
        return uomRepository.save(uom);  // ask this part
    }

    public void update(Uom uom) throws Exception {

        Uom foundedUom = uomRepository.findByName(uom.getName()).orElseThrow(()->new Exception("There is no Uom"));
        uom.setId(foundedUom.getId());
        uomRepository.save(uom);
    }

    public List<Uom> readAll(){
        return  uomRepository.findAll(Sort.by("name"));

    }

    public Uom readById(Integer id) throws Exception {
        return uomRepository.findById(id).orElseThrow(()->new Exception("There is no Uom to update "));
    }

    public void deleteById(Integer id) throws Exception {

        Uom foundedUom=readById(id);
        List<Product> products = productService.readAllByUom(foundedUom);

        if(products.size()>0) {
            throw new Exception("This Uom can not be deleted");
        }
        foundedUom.setName(foundedUom.getName()+"-"+foundedUom.getId());
        foundedUom.setIsDeleted(true);
        uomRepository.save(foundedUom);
    }
}
