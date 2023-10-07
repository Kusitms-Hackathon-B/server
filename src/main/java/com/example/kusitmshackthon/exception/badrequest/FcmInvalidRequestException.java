package com.example.kusitmshackthon.exception.badrequest;

import com.example.kusitmshackthon.exception.CustomExceptionContext;

public class FcmInvalidRequestException extends BadRequestException {
    public FcmInvalidRequestException() {
        super(CustomExceptionContext.FCM_MESSAGING_FAILED);
    }
}
