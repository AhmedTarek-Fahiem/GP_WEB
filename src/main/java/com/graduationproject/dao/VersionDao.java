package com.graduationproject.dao;


import com.graduationproject.model.Version;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VersionDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Version getVersion() {
        Version version = (Version)(sessionFactory.getCurrentSession().createQuery("from Version").uniqueResult());
        if (sessionFactory.getCurrentSession().contains(version))
            return version;
        else
            return null;
    }
}
