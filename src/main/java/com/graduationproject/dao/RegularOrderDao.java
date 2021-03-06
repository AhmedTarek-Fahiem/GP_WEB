package com.graduationproject.dao;


import com.graduationproject.model.RegularOrder;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class RegularOrderDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void addRegularOrder(RegularOrder regularOrder) {
        sessionFactory.getCurrentSession().saveOrUpdate(regularOrder);
    }

    public void addRegularOrders(List<RegularOrder> regularOrders) {
        regularOrders.forEach(regularOrder -> sessionFactory.getCurrentSession().saveOrUpdate(regularOrder));
    }

    @SuppressWarnings("unchecked")
    public List<RegularOrder> getAllRegularOrders(String patient_id) {
        return sessionFactory.getCurrentSession().createQuery("from RegularOrder regularOrder where regularOrder.patient_id = :patient_id").setString("patient_id", patient_id).list();
    }

    @SuppressWarnings("unchecked")
    public List<RegularOrder> getPrescriptionRegularOrders(String prescription_id) {
        return sessionFactory.getCurrentSession().createQuery("from RegularOrder regularOrder where regularOrder.prescription_id = :prescription_id").setString("prescription_id", prescription_id).list();
    }
}
