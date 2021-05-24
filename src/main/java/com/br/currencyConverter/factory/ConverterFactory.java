package com.br.currencyConverter.factory;

import com.br.currencyConverter.enums.Currencies;
import com.br.currencyConverter.interfaces.ConverterInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.InstanceNotFoundException;
import java.text.MessageFormat;
import java.util.List;

@Component
public class ConverterFactory {

    @Autowired
    private List<ConverterInterface> interfaces;

    public ConverterInterface getInstance(Currencies type) throws InstanceNotFoundException {
        return interfaces.stream().filter(i -> i.responsible(type)).findFirst().orElseThrow(() -> new InstanceNotFoundException(MessageFormat.format("Não existe implementação pra esse tipo type {0}", type)));
    }

}
