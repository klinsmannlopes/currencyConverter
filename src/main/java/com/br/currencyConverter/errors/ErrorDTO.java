package com.br.currencyConverter.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ErrorDTO {

    private Integer errorCode;
    private String errorSource;
    private String errorReason;
    private String errorDetail;

    public ErrorDTO(Integer errorCode, String errorSource, String errorReason) {
        this.errorCode = errorCode;
        this.errorSource = errorSource;
        this.errorReason = errorReason;
    }

}
