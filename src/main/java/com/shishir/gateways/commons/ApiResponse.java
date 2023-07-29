package com.shishir.gateways.commons;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class ApiResponse {
    private int httpStatus;
    private String message;
    private Object data;
    private List<String> errors;

    public ApiResponse badRequest() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
        apiResponse.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        return apiResponse;
    }

    public ApiResponse success(Object data) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(data);
        apiResponse.setMessage(HttpStatus.OK.getReasonPhrase());
        apiResponse.setHttpStatus(HttpStatus.OK.value());
        return apiResponse;
    }
}
