package com.br.currencyConverter.dtos.inputs;

import com.br.currencyConverter.enums.Currencies;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class TransactionInputDTO {

    @NotNull
    private String idUser;

    @NotNull
    private double originValue;

    @NotNull
    private Currencies destinyCurrency;

}
