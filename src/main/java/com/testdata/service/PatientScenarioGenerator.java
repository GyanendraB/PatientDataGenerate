package com.testdata.service;

import com.github.javafaker.Faker;
import com.testdata.model.Patient;
import java.time.ZoneId;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class PatientScenarioGenerator {

    private final Faker faker = new Faker();

    public Patient registeredPatient(String ssn, String testRunId) {
        return basePatient(ssn, testRunId, "REGISTERED", "GENERAL", "NA");
    }

    public Patient admittedPatient(String ssn, String testRunId) {
        return basePatient(ssn, testRunId, "ADMITTED", "INPATIENT", "WARD-" + faker.number().numberBetween(1, 200));
    }

    public Patient icuPatient(String ssn, String testRunId) {
        return basePatient(ssn, testRunId, "ICU", "CRITICAL_CARE", "ICU-" + faker.number().numberBetween(1, 50));
    }

    public Patient dischargedPatient(String ssn, String testRunId) {
        return basePatient(ssn, testRunId, "DISCHARGED", "GENERAL", "NA");
    }

    private Patient basePatient(String ssn, String testRunId, String status, String department, String bedNumber) {
        Patient patient = new Patient();
        patient.setPatientId("P-" + UUID.randomUUID().toString().substring(0, 8));
        patient.setName(faker.name().fullName());
        patient.setDob(faker.date().birthday(18, 90).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
        patient.setPhone(faker.phoneNumber().cellPhone());
        patient.setSsn(ssn);
        patient.setStatus(status);
        patient.setDepartment(department);
        patient.setBedNumber(bedNumber);
        patient.setTestRunId(testRunId);
        return patient;
    }
}
