package com.man.fota.enums;

public enum FileOperations {

    PROCESSING(".processing"), PROCESSED(".processed");

    private String details;

    FileOperations(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
}
