package com.devsu.operations.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import java.util.List;

@Data
@AllArgsConstructor
public class OperationGenericClientException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;
    private byte[] body;
    private List<OperationSubError> subErrors;
}
