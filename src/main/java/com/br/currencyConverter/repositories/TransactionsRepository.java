package com.br.currencyConverter.repositories;


import com.br.currencyConverter.models.transactions.Transactions;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TransactionsRepository extends ReactiveCrudRepository<Transactions,Integer> {

    Flux<Transactions> findAllByUserCode(String userCode);

}
