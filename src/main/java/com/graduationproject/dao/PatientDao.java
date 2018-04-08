package com.graduationproject.dao;


import com.graduationproject.model.Patient;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PatientDao {

    @Autowired
    private SessionFactory sessionFactory;

    public boolean addPatient(Patient patient) {
        sessionFactory.getCurrentSession().saveOrUpdate(patient);
        return sessionFactory.getCurrentSession().contains(patient);
    }

    public Patient getPatient(String patient_id) {
        return (Patient) sessionFactory.getCurrentSession().get(Patient.class, patient_id);
    }

    public Patient getPatient(String username, String password) {
        Patient patient = (Patient)(sessionFactory.getCurrentSession().createQuery("from Patient patient where patient.username = :username and patient.password = :password").setString("username", username).setString("password", password).uniqueResult());
        if (sessionFactory.getCurrentSession().contains(patient))
            return patient;
        else
            return null;
    }

    @SuppressWarnings("unchecked")
    public int checkPatient(String username, String email) {
        List<Patient> patients = sessionFactory.getCurrentSession().createQuery("from Patient patient where patient.username = :username or patient.email = :email").setString("username", username).setString("email", email).list();
        if (patients.size() > 0) {
            if (patients.get(0).getEmail().equals(email))
                return 2;
            else if (patients.get(0).getUsername().equals(username))
                return 1;
        }
        return 0;
    }

    private Patient getPatientByUsername(String username) {
        Patient patient = (Patient)(sessionFactory.getCurrentSession().createQuery("from Patient patient where patient.username = :username").setString("username", username).uniqueResult());
        if (sessionFactory.getCurrentSession().contains(patient))
            return patient;
        else
            return null;
    }

    public boolean setPatientPIN(String username, String PatientPIN) {
        Patient patient = getPatientByUsername(username);
        if (patient != null) {
            patient.setPIN(PatientPIN);
            sessionFactory.getCurrentSession().update(patient);
            return true;
        } else
            return false;
    }

    public Patient accessPatient(String username, String patientPIN){
        Patient patient = getPatientByUsername(username);
        if (patient != null) {
            if (patient.getPIN().equals(patientPIN))
                return patient;
        }
        return null;
    }
}
