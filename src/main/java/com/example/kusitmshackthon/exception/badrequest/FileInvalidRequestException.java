package com.example.kusitmshackthon.exception.badrequest;

import com.example.kusitmshackthon.exception.CustomExceptionContext;

public class FileInvalidRequestException extends BadRequestException {
    public FileInvalidRequestException() {
        super(CustomExceptionContext.FILE_INVALID_REQUEST);
    }
}
