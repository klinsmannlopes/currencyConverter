package com.br.currencyConverter.exchangerate;

import com.br.currencyConverter.enums.Currencies;
import com.br.currencyConverter.factory.ConverterFactory;
import com.br.currencyConverter.interfaces.ConverterInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import javax.management.InstanceNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class ConverterTest {

    @Autowired
    private ConverterFactory converterFactory;

    private ConverterInterface getInterfaceImplTest(Currencies type) throws InstanceNotFoundException {
        return converterFactory.getInstance(type);
    }

    @Test
    @DisplayName("Check from euro to real")
    public void checkConverterBRLTest() throws InstanceNotFoundException {
        double rateBRL = 6.499622;
        double euro = 12;
        ConverterInterface converterInterface = getInterfaceImplTest(Currencies.BRL);
        double result = converterInterface.converter(rateBRL, euro);
        assertEquals(77.995464, result);
    }

    @Test
    @DisplayName("Check from euro to dolar")
    public void checkConverterUSDTest() throws InstanceNotFoundException {
        double rateUSD = 1.221068;
        double euro = 12;
        ConverterInterface converterInterface = getInterfaceImplTest(Currencies.USD);
        double result = converterInterface.converter(rateUSD, euro);
        assertEquals(14.652816000000001, result);
    }

    @Test
    @DisplayName("Check from euro to euro")
    public void checkConverterEURTest() throws InstanceNotFoundException {
        double rateEUR = 1;
        double euro = 12;
        ConverterInterface converterInterface = getInterfaceImplTest(Currencies.EUR);
        double result = converterInterface.converter(rateEUR, euro);
        assertEquals(12.0, result);
    }

    @Test
    @DisplayName("Check from euro to yenes")
    public void checkConverterJPYTest() throws InstanceNotFoundException {
        double rateJPY = 132.835743;
        double euro = 12;
        ConverterInterface converterInterface = getInterfaceImplTest(Currencies.EUR);
        double result = converterInterface.converter(rateJPY, euro);
        assertEquals(1594.0289160000002, result);
    }

}
