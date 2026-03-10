package com.testdata.generator;

import com.testdata.model.Patient;

public class PatientScenarioGenerator {

    public static Patient registeredPatient() {

        Patient patient = PatientGenerator.generatePatient();

        patient.setStatus("REGISTERED");
        patient.setDepartment("OPD");

        return patient;
    }

    public static Patient admittedPatient() {

        Patient patient = PatientGenerator.generatePatient();

        patient.setStatus("ADMITTED");
        patient.setDepartment("WARD");
        patient.setBedNumber("W-" + (int) (Math.random() * 100));

        return patient;
    }

    public static Patient icuPatient() {

        Patient patient = PatientGenerator.generatePatient();

        patient.setStatus("ICU");
        patient.setDepartment("ICU");
        patient.setBedNumber("ICU-" + (int) (Math.random() * 20));

        return patient;
    }
}
