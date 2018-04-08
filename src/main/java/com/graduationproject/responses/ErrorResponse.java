package com.graduationproject.responses;

public class ErrorResponse {

    private int error;

    public ErrorResponse(int error) {
        this.error = error;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
