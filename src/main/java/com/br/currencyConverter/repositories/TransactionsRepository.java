package com.br.currencyConverter.repositories;


import com.br.currencyConverter.models.transactions.Transactions;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TransactionsRepository extends ReactiveCrudRepository<Transactions,Integer> {

}
