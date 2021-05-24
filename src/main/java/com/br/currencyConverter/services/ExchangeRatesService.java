package com.br.currencyConverter.services;

import com.br.currencyConverter.apis.exchangerate.ExchangeRatesApi;
import com.br.currencyConverter.dtos.outputs.RateDTO;
import com.br.currencyConverter.exceptions.BusinessRuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRatesService {

    @Autowired
    private ExchangeRatesApi exchangeRatesApi;

    public RateDTO getListRates() throws BusinessRuleException {
        RateDTO rateDTO = exchangeRatesApi.getListRates("110ed70748e8b3174324927a9d226b44", "BRL,USD,EUR,JPY");
        return rateDTO;
    }


}
