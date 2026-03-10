package com.testdata.dto;

public record FullAdmissionResponse(
        String patientId,
        String doctorId,
        String appointmentId,
        String admissionId,
        String bedId,
        String billingId,
        String status
) {
}
