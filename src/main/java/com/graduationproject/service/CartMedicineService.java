package com.graduationproject.service;


import com.graduationproject.dao.CartMedicineDao;
import com.graduationproject.model.CartMedicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CartMedicineService {

    @Autowired
    private CartMedicineDao cartMedicineDao;

    @Transactional
    public void addCartMedicines(List<CartMedicine> cartMedicines) {
        cartMedicineDao.addCartMedicines(cartMedicines);
    }

    @Transactional
    public List<CartMedicine> getAllCartMedicines(String prescription_id) {
        return cartMedicineDao.getAllCartMedicines(prescription_id);
    }
}
