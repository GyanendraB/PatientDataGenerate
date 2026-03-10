package com.testdata;

import com.testdata.generator.PatientGenerator;
import com.testdata.model.Patient;

public class Main {

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {

            Patient patient = PatientGenerator.generatePatient();

            System.out.println(patient);
        }
    }
}
