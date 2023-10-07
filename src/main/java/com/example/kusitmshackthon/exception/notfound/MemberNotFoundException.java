package com.example.kusitmshackthon.exception.notfound;

import static com.example.kusitmshackthon.exception.CustomExceptionContext.NOT_FOUND_MEMBER;

public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException() {
        super(NOT_FOUND_MEMBER);
    }
}
