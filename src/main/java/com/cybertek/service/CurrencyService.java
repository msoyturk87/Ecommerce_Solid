package com.cybertek.service;

import com.cybertek.dto.CurrencyDTO;
import com.cybertek.entity.Currency;


import java.util.List;

public interface CurrencyService {



   Currency create(CurrencyDTO currencyDTO);

   void update(CurrencyDTO currencyDTO) ;

   List<CurrencyDTO> readAll();

   CurrencyDTO readById(Integer id) ;

   CurrencyDTO readByName(String name) ;

   void deleteById(Integer id) ;



}