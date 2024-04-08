package com.devsu.operations.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class OperationUnauthorizedException extends RuntimeException{
    private String code;
    private HttpStatus httpStatus;
    public OperationUnauthorizedException(String code, String message, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
