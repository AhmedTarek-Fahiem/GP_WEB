package com.graduationproject.responses;

import java.util.List;

public class PrescriptionResponse {

    private List<PrescriptionResultResponse> result;
    private int success_sync_offline;
    private int success_prescription;

    public PrescriptionResponse(List<PrescriptionResultResponse> result, int success_sync_offline, int success_prescription) {
        this.result = result;
        this.success_sync_offline = success_sync_offline;
        this.success_prescription = success_prescription;
    }

    public List<PrescriptionResultResponse> getResult() {
        return result;
    }

    public void setResult(List<PrescriptionResultResponse> result) {
        this.result = result;
    }

    public int getSuccess_sync_offline() {
        return success_sync_offline;
    }

    public void setSuccess_sync_offline(int success_sync_offline) {
        this.success_sync_offline = success_sync_offline;
    }

    public int getSuccess_prescription() {
        return success_prescription;
    }

    public void setSuccess_prescription(int success_prescription) {
        this.success_prescription = success_prescription;
    }
}
