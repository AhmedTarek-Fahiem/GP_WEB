package com.graduationproject.responses;

import com.graduationproject.model.CartMedicine;
import com.graduationproject.model.History;
import com.graduationproject.model.Prescription;
import com.graduationproject.model.RegularOrder;

import java.util.List;

public class PrescriptionResultResponse {

    private Prescription prescription;
    private List<CartMedicine> carts;
    private int success_regular;
    private List<RegularOrder> regulars;
    private History history;


    public PrescriptionResultResponse(Prescription prescription, List<CartMedicine> carts, int success_regular, List<RegularOrder> regulars, History history) {
        this.prescription = prescription;
        this.carts = carts;
        this.success_regular = success_regular;
        this.regulars = regulars;
        this.history = history;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public List<CartMedicine> getCarts() {
        return carts;
    }

    public void setCarts(List<CartMedicine> carts) {
        this.carts = carts;
    }

    public int getSuccess_regular() {
        return success_regular;
    }

    public void setSuccess_regular(int success_regular) {
        this.success_regular = success_regular;
    }

    public List<RegularOrder> getRegulars() {
        return regulars;
    }

    public void setRegulars(List<RegularOrder> regulars) {
        this.regulars = regulars;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }
}
