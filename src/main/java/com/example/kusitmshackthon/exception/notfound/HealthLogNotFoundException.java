package com.example.kusitmshackthon.exception.notfound;

import static com.example.kusitmshackthon.exception.CustomExceptionContext.NOT_FOUND_HEALTH_LOG;

public class HealthLogNotFoundException extends NotFoundException {
    public HealthLogNotFoundException() {
        super(NOT_FOUND_HEALTH_LOG);
    }
}
