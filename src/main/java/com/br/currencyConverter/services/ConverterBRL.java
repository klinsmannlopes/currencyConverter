package com.br.currencyConverter.services;

import com.br.currencyConverter.apis.exchangerate.ExchangeRatesApi;
import com.br.currencyConverter.dtos.inputs.TransactionInputDTO;
import com.br.currencyConverter.dtos.outputs.RateDTO;
import com.br.currencyConverter.enums.Currencies;
import com.br.currencyConverter.exceptions.BusinessRuleException;
import com.br.currencyConverter.interfaces.ConverterInterface;
import com.br.currencyConverter.models.transactions.Transactions;
import com.br.currencyConverter.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

@Service
public class ConverterBRL implements ConverterInterface {

    @Autowired
    private ExchangeRatesApi exchangeRatesApi;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Override
    public boolean responsible(Currencies type) {
        return type == Currencies.BRL;
    }

    @Override
    public RateDTO getListRates() throws BusinessRuleException {
        RateDTO rateDTO = exchangeRatesApi.getListRates();
        return rateDTO;
    }

    @Override
    public double converter(double rate, double originValue) {
        return rate * originValue;
    }

    @Override
    public Mono<Transactions> converterAndSave(TransactionInputDTO transactionInputDTO) throws BusinessRuleException {
        RateDTO rateDTO = getListRates();
        double rateUsed = rateDTO.getRates().getBRL();
        double destinyValue = converter(rateUsed, transactionInputDTO.getOriginValue());

        Transactions transaction = new Transactions(
                transactionInputDTO.getIdUser(),
                Currencies.EUR,
                transactionInputDTO.getOriginValue(),
                transactionInputDTO.getDestinyCurrency(),
                destinyValue,
                rateUsed,
                new Timestamp(System.currentTimeMillis())
        );

        return transactionsRepository.save(transaction);
    }
}
