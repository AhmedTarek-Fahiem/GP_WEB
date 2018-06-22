package com.graduationproject.dao;

import com.graduationproject.model.SecurityToken;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityTokenDao {

    @Autowired
    private SessionFactory sessionFactory;

    public SecurityToken getToken(String username) {
        return (SecurityToken)(sessionFactory.getCurrentSession().get(SecurityToken.class, username));
    }

    public void addToken(SecurityToken securityToken) {
        sessionFactory.getCurrentSession().saveOrUpdate(securityToken);
    }

    public int updateSecurityToken(SecurityToken securityToken) {
        return sessionFactory.getCurrentSession().createQuery("update SecurityToken token set token.token = :token and token.last_used = :last_used where token.username = :username").setString("token", securityToken.getToken()).setDate("last_used", securityToken.getLast_used()).setString("username", securityToken.getUsername()).executeUpdate();
    }
}
