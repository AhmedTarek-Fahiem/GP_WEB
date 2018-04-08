package com.graduationproject.responses;

import java.util.List;

public class PatientDetailsResponse {

    private int success;
    private List<PatientDetails> patientDetails;


    public PatientDetailsResponse(int success, List<PatientDetails> patientDetails) {
        this.success = success;
        this.patientDetails = patientDetails;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<PatientDetails> getPatientDetails() {
        return patientDetails;
    }

    public void setPatientDetails(List<PatientDetails> patientDetails) {
        this.patientDetails = patientDetails;
    }
}
