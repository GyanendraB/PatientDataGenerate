package com.testdata.generator;

import com.github.javafaker.Faker;
import com.testdata.model.Patient;
import com.testdata.util.SSNGenerator;

import java.util.UUID;

public class PatientGenerator {

    private static final Faker faker = new Faker();

    public static Patient generatePatient() {

        Patient patient = new Patient();

        patient.setPatientId("P-" + UUID.randomUUID().toString().substring(0, 6));
        patient.setName(faker.name().fullName());
        patient.setDob(faker.date().birthday().toString());
        patient.setPhone(faker.phoneNumber().cellPhone());
        patient.setSsn(SSNGenerator.generate());

        return patient;
    }
}
