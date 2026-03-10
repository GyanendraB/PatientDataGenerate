package com.testdata.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class SsnPool {

    @Id
    private String ssn;

    private boolean used;
}
