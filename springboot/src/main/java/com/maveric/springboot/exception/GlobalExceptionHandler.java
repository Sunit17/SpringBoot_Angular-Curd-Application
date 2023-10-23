package com.maveric.springboot.exception;

import com.maveric.springboot.Dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class  GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponse> resourceNotFound( ResourceNotFound resourceNotFound)
    {
      String message=resourceNotFound.getMessage();
      ApiResponse apiResponse=new ApiResponse(message,false);
      return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodNotValid(MethodArgumentNotValidException ex)
    {
        Map<String,String> resp=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldname = ((FieldError)error).getField();
            String message= error.getDefaultMessage();
            resp.put(fieldname,message);
        });
        return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
    }


}
