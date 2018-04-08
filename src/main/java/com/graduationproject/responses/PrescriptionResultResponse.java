package com.graduationproject.responses;

import com.graduationproject.model.CartMedicine;
import com.graduationproject.model.Prescription;
import com.graduationproject.model.RegularOrder;

import java.util.List;

public class PrescriptionResultResponse {

    private List<CartMedicine> carts;
    private List<Prescription> prescriptions;
    private int success_regular;
    private List<RegularOrder> regulars;

    public PrescriptionResultResponse(List<CartMedicine> carts, List<Prescription> prescriptions, int success_regular, List<RegularOrder> regulars) {
        this.carts = carts;
        this.prescriptions = prescriptions;
        this.success_regular = success_regular;
        this.regulars = regulars;
    }

    public List<CartMedicine> getCarts() {
        return carts;
    }

    public void setCarts(List<CartMedicine> carts) {
        this.carts = carts;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
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
}
