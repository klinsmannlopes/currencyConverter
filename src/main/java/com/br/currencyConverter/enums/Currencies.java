package com.br.currencyConverter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Currencies {

    BRL("BRL", "Real"),
    USD("USD", "Dolar"),
    EUR("EUR", "EUR"),
    JPY("JPY", "Iene");

    private String code;
    private String description;

}
