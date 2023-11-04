package com.todomanagement.app.exception;

import java.util.Date;

public class ErrorDetails {
    private Date timestamp;
    private String status;
    private String description;

    public ErrorDetails(Date timestamp, String status, String description) {
        this.timestamp = timestamp;
        this.status = status;
        this.description = description;
    }
    public ErrorDetails(){

    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
