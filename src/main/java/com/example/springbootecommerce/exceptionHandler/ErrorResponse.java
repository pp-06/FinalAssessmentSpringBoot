package com.example.springbootecommerce.exceptionHandler;

public class ErrorResponse {
    private int errorCode;
    private String msg;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public String getMessage() {
        return msg;
    }
}
