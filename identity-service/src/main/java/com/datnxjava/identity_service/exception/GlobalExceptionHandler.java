package com.datnxjava.identity_service.exception;

import com.datnxjava.identity_service.dto.request.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

//declare class to announce that whenever exception happen, this class will handle it
@ControllerAdvice
public class GlobalExceptionHandler {
    //annotation for exception
    @ExceptionHandler(value = Exception.class) //if happen any exception about Exception, it will process centrally here
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception){ //declare parameter, to inject exception into response func to process it
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode()); //set code to exception code
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage()); // get message of exception

        return ResponseEntity.badRequest().body(apiResponse); //return apiReponse
    }

    @ExceptionHandler(value = AppException.class) //if happen any exception about AppException, it will process centrally here
    ResponseEntity<ApiResponse> handlingAppException(AppException exception){ //declare parameter, to inject exception into response func to process it
        //get Errorcode
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode()); // get error code
        apiResponse.setMessage(errorCode.getMessage()); // get message of error code

        return ResponseEntity.badRequest().body(apiResponse); //return apiReponse
    }


    // exception for MethodArgumentNotValidException
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception){
        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();

        //đề phòng trường hợp là 1 enum chưa từng được khai báo => auto gán vào KEY_INVALID
        ErrorCode errorCode = ErrorCode.KEY_INVALID;

        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException e){

        }

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode()); //set code to exception code
        apiResponse.setMessage(errorCode.getMessage()); // get default message of exception

        return ResponseEntity.badRequest().body(apiResponse); //return apiReponse
    }
}
