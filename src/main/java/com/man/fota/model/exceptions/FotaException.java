package com.man.fota.model.exceptions;

public class FotaException extends Exception {
    public FotaException() {
    }

    public FotaException(String message) {
        super(message);
    }

    public FotaException(String message, Throwable cause) {
        super(message, cause);
    }
}
