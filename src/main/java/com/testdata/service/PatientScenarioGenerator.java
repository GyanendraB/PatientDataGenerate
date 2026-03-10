package com.testdata.service;

import com.github.javafaker.Faker;
import com.testdata.model.Patient;
import java.time.ZoneId;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class PatientScenarioGenerator {

    private final Faker faker = new Faker();

    public Patient registeredPatient(String ssn) {
        Patient patient = new Patient();
        patient.setPatientId(UUID.randomUUID().toString());
        patient.setName(faker.name().fullName());
        patient.setDob(faker.date().birthday(18, 90).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
        patient.setPhone(faker.phoneNumber().cellPhone());
        patient.setSsn(ssn);
        patient.setStatus("REGISTERED");
        patient.setDepartment("GENERAL");
        patient.setBedNumber("NA");
        return patient;
    }

    public Patient admittedPatient(Patient patient) {
        patient.setStatus("ADMITTED");
        patient.setDepartment("WARD");
        patient.setBedNumber("W-" + faker.number().numberBetween(1, 100));
        return patient;
    }

    public Patient icuPatient(Patient patient) {
        patient.setStatus("ICU");
        patient.setDepartment("ICU");
        patient.setBedNumber("ICU-" + faker.number().numberBetween(1, 20));
        return patient;
    }
}
