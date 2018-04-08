package com.graduationproject.dao;


import com.graduationproject.model.CartMedicine;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class CartMedicineDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void addCartMedicines(List<CartMedicine> cartMedicines) {
        cartMedicines.forEach(cartMedicine -> sessionFactory.getCurrentSession().saveOrUpdate(cartMedicine));
    }

    @SuppressWarnings("unchecked")
    public List<CartMedicine> getAllCartMedicines(String prescription_id) {
        return sessionFactory.getCurrentSession().createQuery("from CartMedicine cartMedicine where cartMedicine.prescription_id = :prescription_id").setString("prescription_id", prescription_id).list();
    }
}
