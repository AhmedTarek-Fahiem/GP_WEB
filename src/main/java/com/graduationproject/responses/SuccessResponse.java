package com.graduationproject.responses;

public class SuccessResponse {

    private int success_prescription;
    private int success_cart;
    private int success_regular;

    public SuccessResponse(int success_prescription, int success_cart, int success_regular) {
        this.success_prescription = success_prescription;
        this.success_cart = success_cart;
        this.success_regular = success_regular;
    }

    public int getSuccess_prescription() {
        return success_prescription;
    }

    public void setSuccess_prescription(int success_prescription) {
        this.success_prescription = success_prescription;
    }

    public int getSuccess_cart() {
        return success_cart;
    }

    public void setSuccess_cart(int success_cart) {
        this.success_cart = success_cart;
    }

    public int getSuccess_regular() {
        return success_regular;
    }

    public void setSuccess_regular(int success_regular) {
        this.success_regular = success_regular;
    }
}
