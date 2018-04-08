package com.graduationproject.responses;

import java.util.List;

public class ResultErrorResponse {

    private List<ErrorResponse> result;

    public ResultErrorResponse(List<ErrorResponse> result) {
        this.result = result;
    }

    public List<ErrorResponse> getResult() {
        return result;
    }

    public void setResult(List<ErrorResponse> result) {
        this.result = result;
    }
}
