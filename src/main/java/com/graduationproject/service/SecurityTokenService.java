package com.graduationproject.service;

import com.graduationproject.dao.SecurityTokenDao;
import com.graduationproject.model.SecurityToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SecurityTokenService {

    @Autowired
    private SecurityTokenDao securityTokenDao;

    @Transactional
    public SecurityToken getToken(String username) {
        return securityTokenDao.getToken(username);
    }

    @Transactional
    public void addToken(SecurityToken securityToken) {
        securityTokenDao.addToken(securityToken);
    }

    @Transactional
    public int updateSecurityToken(SecurityToken securityToken) {
        return securityTokenDao.updateSecurityToken(securityToken);
    }
}
