package com.graduationproject.dao;


import com.graduationproject.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public boolean addUser(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
        return sessionFactory.getCurrentSession().contains(user);
    }

    public User getUser(String user_id) {
        return (User) sessionFactory.getCurrentSession().get(User.class, user_id);
    }

    public User getUser(String username, String password) {
        User user = (User)(sessionFactory.getCurrentSession().createQuery("from User user where user.username = :username and user.password = :password").setString("username", username).setString("password", password).uniqueResult());
        if (sessionFactory.getCurrentSession().contains(user))
            return user;
        else
            return null;
    }

    public User getUserByUsername(String username) {
        User user = (User)(sessionFactory.getCurrentSession().createQuery("from User user where user.username = :username").setString("username", username).uniqueResult());
        if (sessionFactory.getCurrentSession().contains(user))
            return user;
        else
            return null;
    }
}
