package com.br.currencyConverter.dtos.inputs;

import com.br.currencyConverter.enums.Currencies;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransactionInputDTO {

    private String idUser;
    private double originValue;
    private Currencies destinyCurrency;

}
