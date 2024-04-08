package com.devsu.finance.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class FinanceUnauthorizedException extends RuntimeException{
    private String code;
    private HttpStatus httpStatus;
    public FinanceUnauthorizedException(String code,String message, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
