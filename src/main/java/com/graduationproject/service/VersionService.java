package com.graduationproject.service;


import com.graduationproject.dao.VersionDao;
import com.graduationproject.model.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VersionService {

    @Autowired
    private VersionDao versionDao;

    @Transactional
    public Version getVersion() {
        return versionDao.getVersion();
    }
}
