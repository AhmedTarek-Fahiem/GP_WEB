package com.graduationproject.dao;


import com.graduationproject.model.Medicine;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class MedicineDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void addMedicine(Medicine medicine) {
        sessionFactory.getCurrentSession().saveOrUpdate(medicine);
    }

    @SuppressWarnings("unchecked")
    public List<Medicine> getAllMedicines() {
        return sessionFactory.getCurrentSession().createQuery("from Medicine").list();
    }

    public Medicine getMedicine(String medicine_id) {
        return (Medicine)(sessionFactory.getCurrentSession().get(Medicine.class, medicine_id));
    }

    public Medicine updateEmployee(Medicine medicine) {
        sessionFactory.getCurrentSession().update(medicine);
        return medicine;
    }

    public boolean deleteMedicine(String medicine_id) {
        Medicine medicine = (Medicine) sessionFactory.getCurrentSession().load(Medicine.class, medicine_id);
        if (medicine != null) {
            this.sessionFactory.getCurrentSession().delete(medicine);
            return true;
        } else
            return false;
    }
}
