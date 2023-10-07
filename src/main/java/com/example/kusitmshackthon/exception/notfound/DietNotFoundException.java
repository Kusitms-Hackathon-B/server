package com.example.kusitmshackthon.exception.notfound;

import static com.example.kusitmshackthon.exception.CustomExceptionContext.NOT_FOUND_DIET;

public class DietNotFoundException extends NotFoundException {
    public DietNotFoundException() {
        super(NOT_FOUND_DIET);
    }
}
