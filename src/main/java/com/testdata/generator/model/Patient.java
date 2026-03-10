package com.testdata.generator.model;

public class Patient {

    private String patientId;
    private String name;
    private String dob;
    private String phone;
    private String ssn;

    public Patient() {
    }

    public Patient(String patientId, String name, String dob, String phone, String ssn) {
        this.patientId = patientId;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.ssn = ssn;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId='" + patientId + '\'' +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", phone='" + phone + '\'' +
                ", ssn='" + ssn + '\'' +
                '}';
    }
}
