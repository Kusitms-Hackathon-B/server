package com.example.kusitmshackthon.exception.notfound;

import static com.example.kusitmshackthon.exception.CustomExceptionContext.NOT_FOUND_FOOD_INFO;

public class FoodInfoNotFoundException extends NotFoundException {
    public FoodInfoNotFoundException() {
        super(NOT_FOUND_FOOD_INFO);
    }
}
