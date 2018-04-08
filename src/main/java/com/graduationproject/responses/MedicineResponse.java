package com.graduationproject.responses;

import com.graduationproject.model.Medicine;

import java.util.List;

public class MedicineResponse {

    private int success;
    private List<Medicine> medicines;

    public MedicineResponse(int success, List<Medicine> medicines) {
        this.success = success;
        this.medicines = medicines;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }
}
