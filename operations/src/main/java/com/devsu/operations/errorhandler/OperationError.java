package com.devsu.operations.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OperationError {
    private String code;
    private HttpStatus httpStatus;
    @Builder.Default
    private String timestamp = LocalDateTime.now().toString();
    private String message;
    private String debugMessage;
    private List<OperationSubError> subErrors;
}
