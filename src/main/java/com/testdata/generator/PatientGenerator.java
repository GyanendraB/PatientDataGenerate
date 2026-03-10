package com.testdata.generator;

import com.github.javafaker.Faker;
import com.testdata.model.Patient;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

public final class PatientGenerator {

    private static final Faker FAKER = new Faker(Locale.ENGLISH);
    private static final DateTimeFormatter DOB_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private PatientGenerator() {
    }

    public static Patient generatePatient() {
        String patientId = "P-" + UUID.randomUUID().toString().substring(0, 6);
        String name = FAKER.name().fullName();
        String dob = FAKER.date().birthday(1, 90)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .format(DOB_FORMAT);
        String phone = FAKER.phoneNumber().cellPhone();
        String ssn = FAKER.idNumber().ssnValid();

        return Patient.builder()
                .patientId(patientId)
                .name(name)
                .dob(dob)
                .phone(phone)
                .ssn(ssn)
                .build();
    }
}
