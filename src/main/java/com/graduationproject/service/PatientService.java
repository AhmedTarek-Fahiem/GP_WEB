package com.graduationproject.service;


import com.graduationproject.dao.PatientDao;
import com.graduationproject.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PatientService {

    @Autowired
    private PatientDao patientDao;

    @Transactional
    public boolean addPatient(Patient patient) {
        return patientDao.addPatient(patient);
    }

    @Transactional
    public Patient getPatient(String patient_id) {
        return patientDao.getPatient(patient_id);
    }

    @Transactional
    public Patient getPatient(String username, String password) {
        return patientDao.getPatient(username, password);
    }

    @Transactional
    public int checkPatient(String username, String email) {
        return patientDao.checkPatient(username, email);
    }

    @Transactional
    public boolean setPatientPIN(String username, String patientPIN) {
        return patientDao.setPatientPIN(username, patientPIN);
    }

    @Transactional
    public Patient accessPatient(String username, String patientPIN){
        return patientDao.accessPatient(username, patientPIN);
    }
}
