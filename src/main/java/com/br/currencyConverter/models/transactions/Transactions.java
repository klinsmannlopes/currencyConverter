package com.br.currencyConverter.models.transactions;


import com.br.currencyConverter.enums.Currencies;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Table
@NoArgsConstructor
@Data
public class Transactions {

    @Id
    private Integer id;

    @Column
    private String idUser;

    @Column
    private Currencies originCurrency;

    @Column
    private double originValue;

    @Column
    private Currencies destinyCurrency;

    @Column
    private double destinyValue;

    @Column
    private double rateUsed;

    @Column
    private Timestamp creationDate;

    public Transactions(String idUser, Currencies originCurrency, double originValue, Currencies destinyCurrency, double destinyValue, double rateUsed, Timestamp timestamp) {
        this.idUser = idUser;
        this.originCurrency = originCurrency;
        this.originValue = originValue;
        this.destinyCurrency = destinyCurrency;
        this.destinyValue = destinyValue;
        this.rateUsed = rateUsed;
        this.creationDate = timestamp;
    }
}
