package com.graduationproject.service;


import com.graduationproject.dao.PrescriptionDao;
import com.graduationproject.model.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PrescriptionService {

    @Autowired
    private PrescriptionDao prescriptionDao;

    @Transactional
    public void addPrescription(Prescription prescription) {
        prescriptionDao.addPrescription(prescription);
    }

    @Transactional
    public List<Prescription> getAllPrescriptions(String patient_id) {
        return prescriptionDao.getAllPrescriptions(patient_id);
    }

    @Transactional
    public Prescription getPrescription(String prescription_id) {
        return prescriptionDao.getPrescription(prescription_id);
    }
}
