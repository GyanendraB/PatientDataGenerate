package com.testdata.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Patient {

    @Id
    private String patientId;

    private String name;
    private String dob;
    private String phone;
    private String ssn;

    private String status;
    private String department;
    private String bedNumber;
}
