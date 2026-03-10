package com.testdata.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    private String patientId;
    private String name;
    private String dob;
    private String phone;
    private String ssn;

    private String status;
    private String department;
    private String bedNumber;
}
