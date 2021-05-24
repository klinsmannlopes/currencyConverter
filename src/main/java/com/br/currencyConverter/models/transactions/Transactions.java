package com.br.currencyConverter.models.transactions;


import com.br.currencyConverter.enums.Currencies;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Table("transactions")
public class Transactions {

    @Id
    private Integer id;

    @Column
    private String userCode;

    @Column
    private String originCurrency;

    @Column
    private double originValue;

    @Column

    private String destinyCurrency;

    @Column
    private double destinyValue;

    @Column
    private double rateUsed;

    @Column
    private Timestamp creationDate;

    public Transactions(String userCode, Currencies originCurrency, double originValue, Currencies destinyCurrency, double destinyValue, double rateUsed, Timestamp timestamp) {
        this.userCode = userCode;
        this.originCurrency = originCurrency.name();
        this.originValue = originValue;
        this.destinyCurrency = destinyCurrency.name();
        this.destinyValue = destinyValue;
        this.rateUsed = rateUsed;
        this.creationDate = timestamp;
    }
}
