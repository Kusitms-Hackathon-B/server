package com.example.kusitmshackthon.exception.badrequest;

import static com.example.kusitmshackthon.exception.CustomExceptionContext.BAD_REQUEST_FOOD;

public class InvalidFoodNameException extends BadRequestException {
    public InvalidFoodNameException() {
        super(BAD_REQUEST_FOOD);
    }
}
