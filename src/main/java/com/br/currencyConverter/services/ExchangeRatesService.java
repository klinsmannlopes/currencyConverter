package com.br.currencyConverter.services;

import com.br.currencyConverter.dtos.inputs.TransactionInputDTO;
import com.br.currencyConverter.exceptions.BusinessRuleException;
import com.br.currencyConverter.factory.ConverterFactory;
import com.br.currencyConverter.interfaces.ConverterInterface;
import com.br.currencyConverter.models.transactions.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.management.InstanceNotFoundException;

@Service
public class ExchangeRatesService {

    @Autowired
    private ConverterFactory converterFactory;

    public Mono<Transactions> converter(TransactionInputDTO transactionInputDTO) throws BusinessRuleException, InstanceNotFoundException {
        ConverterInterface converterInterface = converterFactory.getInstance(transactionInputDTO.getDestinyCurrency());
        Mono<Transactions> transaction = converterInterface.converterAndSave(transactionInputDTO);

        return transaction.switchIfEmpty(
                monoResponseStatusBadRequest()
        );

    }

    public <T> Mono<T> monoResponseStatusBadRequest() {
        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ocorreu algum erro ao tentar salvar a transação"));
    }

}
