package com.example.springbootecommerce.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    // NOT FOUND Exception
    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<ErrorResponse> exceptionCategoryHandler(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.NOT_FOUND);
    }

    // NOT FOUND Exception
    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ErrorResponse> exceptionProductHandler(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.NOT_FOUND);
    }


    // ID NOT FOUND Exception
    @ExceptionHandler(IdException.class)
    public ResponseEntity<ErrorResponse> exceptionIdNotFound(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.BAD_REQUEST);
    }


    // BAD REQUEST Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionBadRequest(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage("Request cannot be handled due to incorrect syntax");
        return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.BAD_REQUEST);
    }


}

