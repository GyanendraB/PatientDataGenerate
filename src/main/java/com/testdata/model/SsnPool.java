package com.testdata.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SsnPool {

    @Id
    private String ssn;

    private boolean used;

    public SsnPool() {
    }

    public SsnPool(String ssn, boolean used) {
        this.ssn = ssn;
        this.used = used;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}