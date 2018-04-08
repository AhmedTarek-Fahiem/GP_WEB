package com.graduationproject.responses;

import com.graduationproject.model.Medicine;

import java.util.Date;
import java.util.List;

public class PatientDetails {

    private String prescription_date;
    private List<Medicine> medicines;
    private String prescription_description;


    public PatientDetails(String prescription_date, List<Medicine> medicines, String prescription_description) {
        this.prescription_date = prescription_date;
        this.medicines = medicines;
        this.prescription_description = prescription_description;
    }

    public String getPrescription_date() {
        return prescription_date;
    }

    public void setPrescription_date(String prescription_date) {
        this.prescription_date = prescription_date;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    public String getPrescription_description() {
        return prescription_description;
    }

    public void setPrescription_description(String prescription_description) {
        this.prescription_description = prescription_description;
    }
}
