package com.br.currencyConverter.interfaces;

import com.br.currencyConverter.dtos.inputs.TransactionInputDTO;
import com.br.currencyConverter.dtos.outputs.RateDTO;
import com.br.currencyConverter.enums.Currencies;
import com.br.currencyConverter.models.transactions.Transactions;
import reactor.core.publisher.Mono;

public interface ConverterInterface {

    boolean responsible(Currencies type);
    RateDTO getListRates();
    double converter(double rate, double originValue);
    Mono<Transactions> converterAndSave(TransactionInputDTO transactionInputDTO);

}
