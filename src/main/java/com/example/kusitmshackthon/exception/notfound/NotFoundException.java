package com.example.kusitmshackthon.exception.notfound;

import com.example.kusitmshackthon.exception.CustomException;
import com.example.kusitmshackthon.exception.CustomExceptionContext;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends CustomException {
    public NotFoundException(CustomExceptionContext context) {
        super(HttpStatus.NOT_FOUND, context.getMessage(), context.getCode());
    }
}
