package com.graduationproject.responses;

import com.graduationproject.model.Patient;

import java.util.List;

public class PatientResponse {

    private int success;
    private int error;
    private List<Patient> patients;
    private String token;

    public PatientResponse(int success, int error, List<Patient> patients, String token) {
        this.success = success;
        this.error = error;
        this.patients = patients;
        this.token = token;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
