package com.graduationproject.responses;

import java.util.List;

public class PrescriptionResponse {

    private List<PrescriptionResultResponse> result;
    private int success_cart;
    private int success_prescription;

    public PrescriptionResponse(List<PrescriptionResultResponse> result, int success_cart, int success_prescription) {
        this.result = result;
        this.success_cart = success_cart;
        this.success_prescription = success_prescription;
    }

    public List<PrescriptionResultResponse> getResult() {
        return result;
    }

    public void setResult(List<PrescriptionResultResponse> result) {
        this.result = result;
    }

    public int getSuccess_cart() {
        return success_cart;
    }

    public void setSuccess_cart(int success_cart) {
        this.success_cart = success_cart;
    }

    public int getSuccess_prescription() {
        return success_prescription;
    }

    public void setSuccess_prescription(int success_prescription) {
        this.success_prescription = success_prescription;
    }
}
