package com.graduationproject.service;


import com.graduationproject.dao.UserDao;
import com.graduationproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public boolean addUser(User user) {
        return userDao.addUser(user);
    }

    @Transactional
    public User getUser(String user_id) {
        return userDao.getUser(user_id);
    }

    @Transactional
    public User getUser(String username, String password) {
        return userDao.getUser(username, password);
    }

    @Transactional
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }
}
