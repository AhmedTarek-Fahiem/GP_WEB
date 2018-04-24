package com.graduationproject.responses;

public class SuccessResponse {

    private int success_prescription;
    private int success_cart;
    private int success_regular;
    private int success_history;
    private String prescription_details;
    private String outOfStockMedicines;

    public SuccessResponse(int success_prescription, int success_cart, int success_regular, int success_history, String prescription_details, String outOfStockMedicines) {
        this.success_prescription = success_prescription;
        this.success_cart = success_cart;
        this.success_regular = success_regular;
        this.success_history = success_history;
        this.prescription_details = prescription_details;
        this.outOfStockMedicines = outOfStockMedicines;
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

    public int getSuccess_history() {
        return success_history;
    }

    public void setSuccess_history(int success_history) {
        this.success_history = success_history;
    }

    public String getPrescription_details() {
        return prescription_details;
    }

    public void setPrescription_details(String prescription_details) {
        this.prescription_details = prescription_details;
    }

    public String getOutOfStockMedicines() {
        return outOfStockMedicines;
    }

    public void setOutOfStockMedicines(String outOfStockMedicines) {
        this.outOfStockMedicines = outOfStockMedicines;
    }
}
