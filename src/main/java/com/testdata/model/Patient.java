package com.testdata.model;

public class Patient {

    private String patientId;
    private String name;
    private String dob;
    private String phone;
    private String ssn;

    private String status;
    private String department;
    private String bedNumber;

    public Patient() {
    }

    public Patient(String patientId, String name, String dob, String phone, String ssn, String status,
                   String department, String bedNumber) {
        this.patientId = patientId;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.ssn = ssn;
        this.status = status;
        this.department = department;
        this.bedNumber = bedNumber;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId='" + patientId + '\'' +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", phone='" + phone + '\'' +
                ", ssn='" + ssn + '\'' +
                ", status='" + status + '\'' +
                ", department='" + department + '\'' +
                ", bedNumber='" + bedNumber + '\'' +
                '}';
    }
}
