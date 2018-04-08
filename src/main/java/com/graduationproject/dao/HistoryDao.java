package com.graduationproject.dao;

import com.graduationproject.model.History;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HistoryDao {

    @Autowired
    private SessionFactory sessionFactory;

    public History getHistory(String history_id) {
        return (History)(sessionFactory.getCurrentSession().get(History.class, history_id));
    }
}
