package com.cybertek.service;


import com.cybertek.dto.UomDTO;
import com.cybertek.entity.Uom;
import com.cybertek.exception.EcommerceException;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface UomService {


     Uom create(UomDTO uomDTO) throws EcommerceException;
     void update(UomDTO uomDTO) throws EcommerceException;
     List<UomDTO> readAll();
     UomDTO readById(Integer id) throws EcommerceException;
     void deleteById(Integer id) throws EcommerceException;
}
