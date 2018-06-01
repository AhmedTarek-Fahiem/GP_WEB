package com.graduationproject.responses;

public class SuccessResponse {

    private int success;
    private String prescription_details;
    private String outOfStockMedicines;

    public SuccessResponse(int success, String prescription_details, String outOfStockMedicines) {
        this.success = success;
        this.prescription_details = prescription_details;
        this.outOfStockMedicines = outOfStockMedicines;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
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
