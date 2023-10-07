package com.example.kusitmshackthon.exception.badrequest;

import com.example.kusitmshackthon.exception.CustomExceptionContext;

public class DuplicateMemberException extends BadRequestException {
    public DuplicateMemberException() {
        super(CustomExceptionContext.DUPLICATE_MEMBER);
    }
}