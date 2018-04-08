package com.graduationproject.service;


import com.graduationproject.dao.MedicineDao;
import com.graduationproject.model.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MedicineService {

    @Autowired
    private MedicineDao medicineDao;

    @Transactional
    public void addMedicine(Medicine medicine) {
        medicineDao.addMedicine(medicine);
    }

    @Transactional
    public List<Medicine> getAllMedicines() {
        return medicineDao.getAllMedicines();
    }

    @Transactional
    public Medicine getMedicine(String medicine_id) {
        return medicineDao.getMedicine(medicine_id);
    }

    @Transactional
    public Medicine updateMedicine(Medicine medicine) {
        return medicineDao.updateEmployee(medicine);
    }

    @Transactional
    public boolean deleteMedicine(String medicine_id) {
        return medicineDao.deleteMedicine(medicine_id);
    }
}
