package com.todomanagement.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Date;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest)
    {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),exception.getMessage(),webRequest.getDescription(false));

   return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(TodoApiException.class)
    public ResponseEntity<ErrorDetails> handleTodoApiException(TodoApiException exception,WebRequest webRequest)
    {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
    }


}
