package com.br.currencyConverter.exchangerate;

import com.br.currencyConverter.enums.Currencies;
import com.br.currencyConverter.exceptions.BusinessRuleException;
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

}
