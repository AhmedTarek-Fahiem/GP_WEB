package com.graduationproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduationproject.dao.UserProfileDao;
import com.graduationproject.model.UserProfile;

@Service("userProfileService")
@Transactional
public class UserProfileService {

    @Autowired
    UserProfileDao userProfileDao;

    public UserProfile findById(String id) {
        return userProfileDao.findById(id);
    }

    public UserProfile findByType(String type){
        return userProfileDao.findByType(type);
    }

    public List<UserProfile> findAll() {
        return userProfileDao.findAll();
    }
}
