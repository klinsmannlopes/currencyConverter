package com.br.currencyConverter.exchangerate;

import static org.junit.jupiter.api.Assertions.*;
import com.br.currencyConverter.apis.exchangerate.ExchangeRatesApi;
import com.br.currencyConverter.dtos.outputs.RateDTO;
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
    public void checkListFeesEmptyTest(){
        RateDTO result = exchangeRatesApi.getListRates();
        assertEquals(true, result.isSuccess());
    }

}
