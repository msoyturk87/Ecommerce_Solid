package com.cybertek.implementation;

import com.cybertek.model.Currency;
import com.cybertek.model.Product;
import com.cybertek.repository.CurrencyRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyServiceImpl {

    private final CurrencyRepository currencyRepository;
    private final ProductServiceImpl productService;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository, ProductServiceImpl productService) {
        this.currencyRepository = currencyRepository;
        this.productService = productService;
    }


    public Currency create(Currency currency) throws Exception {

        Optional<Currency> foundedCurrency = currencyRepository.findByNameAndSymbol(currency.getName(),currency.getSymbol());

        if(foundedCurrency.isPresent()) {
            throw new Exception(currency.getName()+" already exist.You can not create! ");
        }
        return currencyRepository.save(currency);
    }

    public void update(Currency currency) throws Exception {

        Optional<Currency> foundedCurrency = currencyRepository.findByNameAndSymbol(currency.getName(), currency.getSymbol());
        if(foundedCurrency.isEmpty())
            throw new Exception(currency.getName()+"  does not exist ");

        currency.setId(foundedCurrency.get().getId());
        currencyRepository.save(currency);

    }

    public List<Currency> readAll(){
        return currencyRepository.findAll(Sort.by("name"));

    }

    public Currency readById(Integer id) throws Exception {

        return currencyRepository.findById(id).orElseThrow(()->new Exception("Currency doesn't exist"));
    }

    public Currency readByName(String name) throws Exception {

        return currencyRepository.findByName(name).orElseThrow(()->new Exception("Currency doesn't exist"));
    }

    public void deleteById(Integer id) throws Exception {


        Currency foundedCurrency = readById(id);

        List<Product> products = productService.readAllByCurrency(foundedCurrency);
        if(products.size()>0){
            throw new Exception("This Currency can not be deleted");
        }

        foundedCurrency.setName(foundedCurrency.getName()+"-"+foundedCurrency.getId());
        foundedCurrency.setSymbol(foundedCurrency.getSymbol()+"-"+foundedCurrency.getId());
        foundedCurrency.setIsDeleted(true);
        currencyRepository.save(foundedCurrency);
    }



}