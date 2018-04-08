package com.graduationproject.model;


import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "version")
@DynamicInsert
public class Version {


    @Id
    @Column(nullable = false, length = 6)
    @ColumnDefault(value = "CURRENT_TIMESTAMP(6)")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ver;



    public Date getVer() {
        return ver;
    }

    public void setVer(Date ver) {
        this.ver = ver;
    }
}
