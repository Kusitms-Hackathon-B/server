package com.example.kusitmshackthon.exception.notfound;

import static com.example.kusitmshackthon.exception.CustomExceptionContext.NOT_FOUND_FOOD;
import static com.example.kusitmshackthon.exception.CustomExceptionContext.NOT_FOUND_MEMBER;

public class FoodNotFoundException extends NotFoundException {
    public FoodNotFoundException() {
        super(NOT_FOUND_FOOD);
    }
}
