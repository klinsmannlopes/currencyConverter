package com.br.currencyConverter.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.br.currencyConverter.enums.BusinessRulesEnum;
import org.springframework.http.HttpStatus;

import com.br.currencyConverter.errors.ErrorDTO;
import lombok.Getter;
import lombok.Setter;

public class BusinessRuleException extends Exception {

    private static final long serialVersionUID = -2145566446362060621L;

    @Getter
    private ErrorDTO errorDTO;

    @Getter @Setter
    private HttpStatus httpStatus;

    public BusinessRuleException(BusinessRulesEnum businessRules, Class<?> originClass) {
        this(businessRules, originClass, null, null);
    }

    public BusinessRuleException(BusinessRulesEnum businessRules, Class<?> originClass, HttpStatus httpStatus) {
        this(businessRules, originClass, null, httpStatus);
    }

    public BusinessRuleException(BusinessRulesEnum businessRules, Class<?> originClass, String detail) {
        this(businessRules, originClass, detail, null);
    }

    public BusinessRuleException(BusinessRulesEnum businessRules, Class<?> originClass, String detail, HttpStatus httpStatus) {
        errorDTO = new ErrorDTO(businessRules.ordinal(), originClass.getCanonicalName(), businessRules.name(), detail);
        setHttpStatus(httpStatus);
    }

    public BusinessRuleException(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }

    @Override
    public String getMessage() {
        List<String> fields = new ArrayList<String>();

        if(getErrorDTO() != null) {
            if(getErrorDTO().getErrorCode() != null)
                fields.add(getErrorDTO().getErrorCode().toString());

            if(getErrorDTO().getErrorReason() != null)
                fields.add(getErrorDTO().getErrorReason());

            if(getErrorDTO().getErrorDetail() != null)
                fields.add(getErrorDTO().getErrorDetail());

            if(getErrorDTO().getErrorSource() != null)
                fields.add(getErrorDTO().getErrorSource());
        }

        return !fields.isEmpty() ?
                fields.stream().collect(Collectors.joining(" - ")) :
                "Unknown Error";
    }

}
