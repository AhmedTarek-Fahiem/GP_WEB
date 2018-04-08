package com.graduationproject.responses;

import java.util.List;

public class ResultSuccessResponse {

    private List<SuccessResponse> success;

    public ResultSuccessResponse(List<SuccessResponse> success) {
        this.success = success;
    }

    public List<SuccessResponse> getSuccess() {
        return success;
    }

    public void setSuccess(List<SuccessResponse> success) {
        this.success = success;
    }
}
