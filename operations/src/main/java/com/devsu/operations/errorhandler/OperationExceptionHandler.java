package com.devsu.operations.errorhandler;


import com.devsu.operations.constants.OperationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class OperationExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(HttpStatusCodeException.class)
    protected ResponseEntity<Object> handleHttpRestClient(HttpStatusCodeException ex) {
        OperationError operationError = null;
        if (ex.getStatusCode().is4xxClientError()) {
            operationError = OperationError.builder().httpStatus(ex.getStatusCode())
                    .code(OperationConstants.PREFIX_CLIENT_ERROR).build();
        } else if (ex.getStatusCode().is5xxServerError()) {
            operationError = OperationError.builder().httpStatus(ex.getStatusCode())
                    .code(OperationConstants.PREFIX_SERVER_ERROR).build();
        }
        operationError.setMessage(ex.getMessage());
        log.error("Error HTTP Request Client: {}", ex.getMessage());
        return buildResponseEntity(operationError);
    }

    @ExceptionHandler(OperationNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(OperationNotFoundException ex) {
        log.error("Entity Not Found: {}", ex.getMessage());
        if (ex.getCode()!=null){
            return buildResponseEntity(OperationError.builder().httpStatus(HttpStatus.NOT_FOUND)
                    .code(ex.getCode())
                    .message(ex.getMessage()).build());

        }else{
            return buildResponseEntity(OperationError.builder().httpStatus(HttpStatus.NOT_FOUND)
                    .code(OperationConstants.PREFIX_CLIENT_ERROR + OperationConstants.NOT_FOUND)
                    .message(ex.getMessage()).build());
        }

    }

    @ExceptionHandler(OperationConflictException.class)
    protected ResponseEntity<Object> handleConflict(OperationConflictException ex) {
        log.error("Conflict: {}", ex.getMessage());
        return buildResponseEntity(OperationError.builder().httpStatus(HttpStatus.CONFLICT)
                .code(OperationConstants.PREFIX_CLIENT_ERROR + OperationConstants.CONFLICT)
                .message(ex.getMessage()).build());
    }

    @ExceptionHandler(OperationUnauthorizedException.class)
    protected ResponseEntity<Object> handleUnauthorized(OperationUnauthorizedException ex) {
        log.error("Unauthorized: {}", ex.getMessage());
        if (ex.getCode() == null){
            OperationError operationError = OperationError.builder().httpStatus(HttpStatus.UNAUTHORIZED)
                    .code(OperationConstants.PREFIX_CLIENT_ERROR + OperationConstants.UNAUTHORIZED)
                    .message(ex.getMessage()).build();
            return buildResponseEntity(operationError);
        }else{
            OperationError operationError = OperationError.builder().httpStatus(ex.getHttpStatus())
                    .code(ex.getCode())
                    .message(ex.getMessage()).build();
            return buildResponseEntity(operationError);
        }
    }

    @ExceptionHandler(OperationGenericServerException.class)
    protected ResponseEntity<Object> handleGenericServerError(OperationGenericServerException ex) {
        OperationError operationError = null;
        if (ex.getCode() != null) {
            operationError = OperationError.builder().httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .code(ex.getCode()).build();
        } else {
            operationError = OperationError.builder().httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .code(OperationConstants.PREFIX_SERVER_ERROR + OperationConstants.INTERNAL_SERVER_ERROR)
                    .build();
        }
        operationError.setMessage(ex.getMessage());
        log.error("Internal Server Error: {}", ex.getMessage());
        return buildResponseEntity(operationError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Bad Request: {}", ex.getBindingResult().getFieldError().toString());
        return buildResponseEntity(OperationError.builder().httpStatus(HttpStatus.BAD_REQUEST)
                .code(OperationConstants.PREFIX_CLIENT_ERROR + OperationConstants.BAD_REQUEST)
                .message(ex.getBindingResult().getFieldError().toString())
                .subErrors(fillValidationErrorsFrom(ex)).build());
    }

    @ExceptionHandler(OperationGenericClientException.class)
    protected ResponseEntity<Object> handleGenericClientException(OperationGenericClientException ex) {
        log.error("Client Error: {}", ex.getMessage());
        return buildResponseEntity(OperationError.builder().httpStatus(ex.getHttpStatus())
                .code(OperationConstants.PREFIX_CLIENT_ERROR).message(ex.getMessage())
                .subErrors(ex.getSubErrors()).build());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleError(Exception ex) {
        log.error("Server Error: {}", ex.getMessage());
        return buildResponseEntity(OperationError.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .code(OperationConstants.PREFIX_SERVER_ERROR + OperationConstants.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage()).build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        return buildResponseEntity(OperationError.builder().httpStatus(HttpStatus.BAD_REQUEST)
                .code(OperationConstants.PREFIX_CLIENT_ERROR + OperationConstants.BAD_REQUEST)
                .message(ex.getMessage()).build());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        String message = ex.getParameterName() + " parameter is missing";
        log.error("Bad Request: {}", message);
        return buildResponseEntity(OperationError.builder().httpStatus(HttpStatus.BAD_REQUEST)
                .code(OperationConstants.PREFIX_CLIENT_ERROR + OperationConstants.BAD_REQUEST)
                .message(message).build());
    }

    private ResponseEntity<Object> buildResponseEntity(OperationError operationError) {
        return new ResponseEntity<>(operationError, operationError.getHttpStatus());
    }

    protected List<OperationSubError> fillValidationErrorsFrom(
            MethodArgumentNotValidException argumentNotValid) {
        List<OperationSubError> subErrorCollection = new ArrayList<>();
        argumentNotValid.getBindingResult().getFieldErrors().get(0).getRejectedValue();
        argumentNotValid.getBindingResult().getFieldErrors().stream().forEach((objError) -> {
            subErrorCollection.add(OperationValidationError.builder().object(objError.getObjectName())
                    .field(objError.getField()).rejectValue(objError.getRejectedValue())
                    .message(objError.getDefaultMessage()).build());
        });
        return subErrorCollection;
    }
}
