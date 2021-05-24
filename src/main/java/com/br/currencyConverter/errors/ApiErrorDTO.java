package com.br.currencyConverter.errors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter @Setter @NoArgsConstructor
public class ApiErrorDTO {

    private List<ErrorDTO> errors;

    @JsonIgnore
    private HttpStatus httpStatus;
}
