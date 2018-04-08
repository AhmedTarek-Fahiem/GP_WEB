package com.graduationproject.service;

import com.graduationproject.dao.HistoryDao;
import com.graduationproject.model.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HistoryService {

    @Autowired
    private HistoryDao historyDao;

    public History getHistory(String history_id) {
        return historyDao.getHistory(history_id);
    }
}
