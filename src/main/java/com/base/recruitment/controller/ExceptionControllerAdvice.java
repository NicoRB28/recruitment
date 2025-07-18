package com.base.recruitment.controller;

import com.base.recruitment.dto.error.ApiError;
import com.base.recruitment.exception.client.ClientServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {


    @ExceptionHandler(ClientServiceException.class)
    public ResponseEntity<ApiError> handleClientServiceException(ClientServiceException exception) {
        return ResponseEntity.status(HttpStatus.resolve(exception.getCodigo()))
                .body(new ApiError(exception.getMessage(), exception.getCodigo()));
    }

}
