package com.cybertek.implementation;

import com.cybertek.dto.CurrencyDTO;
import com.cybertek.entity.Currency;
import com.cybertek.entity.Product;
import com.cybertek.exception.EcommerceException;
import com.cybertek.mapper.MapperUtil;
import com.cybertek.repository.CurrencyRepository;
import com.cybertek.service.CurrencyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final MapperUtil mapperUtil;
    private final CurrencyRepository currencyRepository;
    private final ProductServiceImpl productService;

    public CurrencyServiceImpl(MapperUtil mapperUtil, CurrencyRepository currencyRepository, ProductServiceImpl productService) {
        this.mapperUtil = mapperUtil;
        this.currencyRepository = currencyRepository;
        this.productService = productService;
    }

    @Override
    public Currency create(CurrencyDTO currencyDTO) throws EcommerceException {
        Currency convertedCurrency = mapperUtil.convert(currencyDTO, new Currency());
        Optional<Currency> foundedCurrency =
                    currencyRepository.findByNameAndSymbol(convertedCurrency.getName(),convertedCurrency.getSymbol());

        if(foundedCurrency.isPresent()) {
            throw new EcommerceException(convertedCurrency.getName()+ " already exist.You can not create! ");
        }
        return currencyRepository.save(convertedCurrency);
    }
    @Override
    public void update(CurrencyDTO currencyDTO) throws EcommerceException {
        Currency convertedCurrency = mapperUtil.convert(currencyDTO, new Currency());
        Optional<Currency> foundedCurrency = currencyRepository.findByNameAndSymbol(convertedCurrency.getName(), convertedCurrency.getSymbol());
        if(foundedCurrency.isEmpty())
            throw new EcommerceException(convertedCurrency.getName()+"  does not exist ");

        convertedCurrency.setId(foundedCurrency.get().getId());
        currencyRepository.save(convertedCurrency);
    }

    @Override
    public List<CurrencyDTO> readAll() {
        List<Currency> list = currencyRepository.findAll();
        return list.stream().map(obj -> mapperUtil.convert(obj,new CurrencyDTO())).collect(Collectors.toList());    }

    @Override
    public CurrencyDTO readById(Integer id) throws EcommerceException {
        Currency foundedCurrency = currencyRepository.findById(id)
                .orElseThrow(()->new EcommerceException("This currency does not exist"));
        return mapperUtil.convert(foundedCurrency,new CurrencyDTO());    }

    @Override
    public CurrencyDTO readByName(String name) throws EcommerceException {
        Currency foundedCurrency = currencyRepository.findByName(name)
                .orElseThrow(() -> new EcommerceException("Currency doesn't exist"));
        return mapperUtil.convert(foundedCurrency,new CurrencyDTO());    }

    @Override
    public void deleteById(Integer id) throws EcommerceException {

        Currency foundedCurrency = currencyRepository.findById(id)
                .orElseThrow(()->new EcommerceException("this currency does not exist"));

        List<Product> products = productService.readAllByCurrency(foundedCurrency);
        if(products.size()>0){
            throw new EcommerceException("This Currency can not be deleted");
        }

        foundedCurrency.setName(foundedCurrency.getName()+"-"+foundedCurrency.getId());
        foundedCurrency.setSymbol(foundedCurrency.getSymbol()+"-"+foundedCurrency.getId());
        foundedCurrency.setIsDeleted(true);
        currencyRepository.save(foundedCurrency);

    }



}