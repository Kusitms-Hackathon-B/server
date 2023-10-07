package com.example.kusitmshackthon.exception.badrequest;

import static com.example.kusitmshackthon.exception.CustomExceptionContext.FILE_UPLOAD_FAILED;

public class FileUploadException extends BadRequestException {
    public FileUploadException() {
        super(FILE_UPLOAD_FAILED);
    }
}
