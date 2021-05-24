package com.br.currencyConverter.dtos.inputs;

import com.br.currencyConverter.enums.Currencies;
import lombok.Getter;

@Getter
public class TransactionInputDTO {

    private String idUser;

    private Currencies originCurrency;

    private double originValue;

    private Currencies destinyCurrency;

}
