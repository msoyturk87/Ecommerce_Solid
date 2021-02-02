package com.cybertek.service;

import com.cybertek.dto.CurrencyDTO;
import com.cybertek.entity.Currency;
import com.cybertek.exception.EcommerceException;


import java.util.List;

public interface CurrencyService {



   Currency create(CurrencyDTO currencyDTO) throws EcommerceException;

   void update(CurrencyDTO currencyDTO) throws EcommerceException;

   List<CurrencyDTO> readAll();

   CurrencyDTO readById(Integer id) throws EcommerceException;

   CurrencyDTO readByName(String name) throws EcommerceException;

   void deleteById(Integer id) throws EcommerceException;



}