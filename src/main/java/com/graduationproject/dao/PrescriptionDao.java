package com.graduationproject.dao;


import com.graduationproject.model.Prescription;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PrescriptionDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void addPrescription(Prescription prescription) {
        sessionFactory.getCurrentSession().saveOrUpdate(prescription);
    }

    @SuppressWarnings("unchecked")
    public List<Prescription> getAllPrescriptions(String patient_id) {
        return sessionFactory.getCurrentSession().createQuery("from Prescription prescription where prescription.patient_id = :patient_id").setString("patient_id", patient_id).list();
    }

    public Prescription getPrescription(String prescription_id) {
        return (Prescription)(sessionFactory.getCurrentSession().get(Prescription.class, prescription_id));
    }
}
