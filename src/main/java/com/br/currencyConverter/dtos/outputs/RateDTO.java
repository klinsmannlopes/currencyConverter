package com.br.currencyConverter.dtos.outputs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class RateDTO {

    private boolean success;
    private Timestamp timestamp;
    private String base;
    private String date;
    private ListRatesCurrenciesDTO rates;

}
