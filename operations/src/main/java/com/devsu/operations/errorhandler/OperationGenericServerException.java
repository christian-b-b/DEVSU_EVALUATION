package com.devsu.operations.errorhandler;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class OperationGenericServerException extends RuntimeException {
    private String code;
    private HttpStatus httpStatus;

    public OperationGenericServerException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }

    public OperationGenericServerException(String message) {
        super(message);
    }
}
