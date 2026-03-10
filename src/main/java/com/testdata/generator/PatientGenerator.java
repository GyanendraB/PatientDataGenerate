package com.testdata.generator;

import com.testdata.model.Patient;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class PatientGenerator {

    private static final List<String> FIRST_NAMES = List.of("John", "Sarah", "Michael", "Emma", "David", "Sophia");
    private static final List<String> LAST_NAMES = List.of("Miller", "Williams", "Johnson", "Brown", "Davis", "Wilson");

    private PatientGenerator() {
    }

    public static Patient generatePatient() {
        String name = random(FIRST_NAMES) + " " + random(LAST_NAMES);
        String dob = randomDob();
        String phone = String.format("555-%03d-%04d", randomInt(100, 999), randomInt(1000, 9999));
        String ssn = String.format("%03d-%02d-%04d", randomInt(100, 999), randomInt(10, 99), randomInt(1000, 9999));
        String patientId = "P-" + UUID.randomUUID().toString().substring(0, 6);

        return new Patient(patientId, name, dob, phone, ssn, null, null, null);
    }

    private static String randomDob() {
        LocalDate start = LocalDate.of(1940, 1, 1);
        LocalDate end = LocalDate.of(2010, 12, 31);
        long randomDay = ThreadLocalRandom.current().nextLong(start.toEpochDay(), end.toEpochDay());
        return LocalDate.ofEpochDay(randomDay).format(DateTimeFormatter.ISO_DATE);
    }

    private static String random(List<String> values) {
        return values.get(ThreadLocalRandom.current().nextInt(values.size()));
    }

    private static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
