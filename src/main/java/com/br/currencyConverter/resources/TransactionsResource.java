package com.br.currencyConverter.resources;


import com.br.currencyConverter.dtos.inputs.TransactionInputDTO;
import com.br.currencyConverter.models.transactions.Transactions;
import com.br.currencyConverter.services.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.management.InstanceNotFoundException;
import javax.validation.Valid;

@RequestMapping("transactions")
@RestController
public class TransactionsResource {

    @Autowired
    private ExchangeRatesService exchangeRatesService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Transactions> save(@Valid @RequestBody TransactionInputDTO transactionInputDTO) throws InstanceNotFoundException {
        return exchangeRatesService.converter(transactionInputDTO);
    }

    @GetMapping("{user}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Transactions> listAll(@PathVariable String user) {
        return exchangeRatesService.findTransactionsByUser(user);
    }

}
