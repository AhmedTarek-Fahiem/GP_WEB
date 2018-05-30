package com.graduationproject.service;

import com.graduationproject.dao.RegularOrderDao;
import com.graduationproject.model.RegularOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RegularOrderService {

    @Autowired
    private RegularOrderDao regularOrderDao;

    @Transactional
    public void addRegularOrder(RegularOrder regularOrder) {
        regularOrderDao.addRegularOrder(regularOrder);
    }

    @Transactional
    public void addRegularOrders(List<RegularOrder> regularOrders) {
        regularOrderDao.addRegularOrders(regularOrders);
    }

    @Transactional
    public List<RegularOrder> getAllRegularOrders(String patient_id) {
        return regularOrderDao.getAllRegularOrders(patient_id);
    }

    @Transactional
    public List<RegularOrder> getPrescriptionRegularOrders(String prescription_id) {
        return regularOrderDao.getPrescriptionRegularOrders(prescription_id);
    }
}
