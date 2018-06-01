package com.graduationproject.responses;

import com.graduationproject.model.CartMedicine;
import com.graduationproject.model.History;
import com.graduationproject.model.Prescription;
import com.graduationproject.model.RegularOrder;

import java.util.List;

public class PrescriptionResultResponse {

    private Prescription prescription;
    private List<CartMedicine> carts;
    private List<RegularOrder> regulars;


    public PrescriptionResultResponse(Prescription prescription, List<CartMedicine> carts, List<RegularOrder> regulars) {
        this.prescription = prescription;
        this.carts = carts;
        this.regulars = regulars;
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

    public List<RegularOrder> getRegulars() {
        return regulars;
    }

    public void setRegulars(List<RegularOrder> regulars) {
        this.regulars = regulars;
    }
}
