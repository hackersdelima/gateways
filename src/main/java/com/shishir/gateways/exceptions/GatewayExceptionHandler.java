package com.shishir.gateways.exceptions;

import com.shishir.gateways.commons.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GatewayExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponse response = new ApiResponse().notFound(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeviceLimitExceededException.class)
    public ResponseEntity<ApiResponse> handleDeviceLimitExceededException(ResourceNotFoundException ex) {
        ApiResponse response = new ApiResponse().badRequest("Devices limit exceeded");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
