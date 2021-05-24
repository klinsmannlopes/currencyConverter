package com.br.currencyConverter.resources;


import com.br.currencyConverter.dtos.inputs.TransactionInputDTO;
import com.br.currencyConverter.exceptions.BusinessRuleException;
import com.br.currencyConverter.factory.ConverterFactory;
import com.br.currencyConverter.models.transactions.Transactions;
import com.br.currencyConverter.services.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequestMapping("transactions")
@RestController
public class TransactionsResource {

    @Autowired
    private ExchangeRatesService exchangeRatesService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Transactions> save(@RequestBody TransactionInputDTO transactionInputDTO) throws BusinessRuleException {
        return exchangeRatesService.converter(transactionInputDTO);
    }

    /*
    @PostMapping
    @ResponseBody
    public ResponseEntity<OperationOutputDTO> create(@RequestBody OperationInputDTO operation) {
        OperationOutputDTO apiResult = operationService.create(operation);

        return new ResponseEntity<OperationOutputDTO>(apiResult, HttpStatus.OK);
    }

     */

}
