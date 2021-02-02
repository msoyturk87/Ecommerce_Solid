package com.cybertek.service;


import com.cybertek.dto.UomDTO;
import com.cybertek.entity.Uom;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface UomService {


     Uom create(UomDTO uomDTO) ;
     void update(UomDTO uomDTO);
     List<UomDTO> readAll();
     UomDTO readById(Integer id) ;
     void deleteById(Integer id);
}
