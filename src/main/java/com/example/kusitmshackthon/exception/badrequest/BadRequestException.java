package com.example.kusitmshackthon.exception.badrequest;

import com.example.kusitmshackthon.exception.CustomException;
import com.example.kusitmshackthon.exception.CustomExceptionContext;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends CustomException {
    public BadRequestException(CustomExceptionContext context) {
        super(HttpStatus.BAD_REQUEST, context.getMessage(), context.getCode());
    }
}