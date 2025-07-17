package com.base.recruitment.controller;

import com.base.recruitment.dto.error.ApiError;
import com.base.recruitment.exception.client.ClientServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {


    @ExceptionHandler(ClientServiceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handleClientServiceException(ClientServiceException exception) {
        log.error("Error: El cliente ya fue registrado previamente");
        return new ApiError(exception.getMessage(), exception.getCodigo());
    }

}
