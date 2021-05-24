package com.br.currencyConverter.services;

import com.br.currencyConverter.dtos.inputs.TransactionInputDTO;
import com.br.currencyConverter.enums.BusinessRulesEnum;
import com.br.currencyConverter.exceptions.BusinessRuleException;
import com.br.currencyConverter.factory.ConverterFactory;
import com.br.currencyConverter.interfaces.ConverterInterface;
import com.br.currencyConverter.models.transactions.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.management.InstanceNotFoundException;

@Service
public class ExchangeRatesService {

    @Autowired
    private ConverterFactory converterFactory;

    public Mono<Transactions> converter(TransactionInputDTO transactionInputDTO) throws BusinessRuleException {
        try {
            ConverterInterface converterInterface = converterFactory.getInstance(transactionInputDTO.getDestinyCurrency());
            Mono<Transactions> transaction = converterInterface.converterAndSave(transactionInputDTO);

            return transaction;
        } catch (InstanceNotFoundException e) {
            throw new BusinessRuleException(BusinessRulesEnum.ERROR_CONVERTER, getClass(), "Error ao converter moedas");
        }

    }

}
