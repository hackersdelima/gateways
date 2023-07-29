package com.shishir.gateways.commons;

import lombok.Builder;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;

@ToString
@Builder
public class ApiResponse {
    private int httpStatus;
    private String message;
    private List<String> errors;

    public ApiResponse badRequest() {
        this.httpStatus = HttpStatus.BAD_REQUEST.value();
        this.message = HttpStatus.BAD_REQUEST.getReasonPhrase();
        return this;
    }

    public ApiResponse success() {
        this.httpStatus = HttpStatus.OK.value();
        this.message = HttpStatus.OK.getReasonPhrase();
        return this;
    }
}
