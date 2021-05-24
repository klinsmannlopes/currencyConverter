package com.br.currencyConverter.exchangerate;

import static org.junit.jupiter.api.Assertions.*;
import com.br.currencyConverter.apis.exchangerate.ExchangeRatesApi;
import com.br.currencyConverter.dtos.outputs.RateDTO;
import com.br.currencyConverter.exceptions.BusinessRuleException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class ExchangeRatesApiTest {

    @Autowired
    private ExchangeRatesApi exchangeRatesApi;

    @Test
    @DisplayName("Check the list of fees get success")
    public void checkListFeesEmpty() throws BusinessRuleException {
        String accessKey = "110ed70748e8b3174324927a9d226b44";
        String symbols = "BRL,USD,EUR,JPY";

        RateDTO result = exchangeRatesApi.getListRates(accessKey, symbols);

        assertEquals(true, result.isSuccess());
    }

}
