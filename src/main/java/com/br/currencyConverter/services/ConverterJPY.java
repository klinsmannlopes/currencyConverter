package com.br.currencyConverter.services;

import com.br.currencyConverter.apis.exchangerate.ExchangeRatesApi;
import com.br.currencyConverter.dtos.inputs.TransactionInputDTO;
import com.br.currencyConverter.dtos.outputs.RateDTO;
import com.br.currencyConverter.enums.Currencies;
import com.br.currencyConverter.interfaces.ConverterInterface;
import com.br.currencyConverter.models.transactions.Transactions;
import com.br.currencyConverter.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ConverterJPY implements ConverterInterface {

    private final static Logger logger = Logger.getLogger(ConverterJPY.class.getName());

    @Autowired
    private ExchangeRatesApi exchangeRatesApi;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Override
    public boolean responsible(Currencies type) {
        return type == Currencies.JPY;
    }

    @Override
    public RateDTO getListRates(){
        RateDTO rateDTO = exchangeRatesApi.getListRates();
        logger.log(Level.INFO, "Listando taxas das moedas de acordo com o dia");
        return rateDTO;
    }

    @Override
    public double converter(double rate, double originValue) {
        logger.log(Level.INFO, "Cálculo da conversão");
        return rate * originValue;
    }

    @Override
    public Mono<Transactions> converterAndSave(TransactionInputDTO transactionInputDTO) {
        logger.log(Level.INFO, "Iniciando conversão da moeda: " + Currencies.EUR + " para " + transactionInputDTO.getDestinyCurrency());
        RateDTO rateDTO = getListRates();
        double rateUsed = rateDTO.getRates().getJPY();
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

        Mono<Transactions> saveTransactions = transactionsRepository.save(transaction);
        logger.log(Level.INFO, "Salvando transação");
        return saveTransactions;
    }
}
