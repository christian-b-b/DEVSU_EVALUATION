package com.devsu.finance.errorhandler;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class FinanceGenericServerException extends RuntimeException {
    private String code;
    private HttpStatus httpStatus;

    public FinanceGenericServerException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }

    public FinanceGenericServerException(String message) {
        super(message);
    }
}
