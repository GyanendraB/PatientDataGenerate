package com.testdata.dto;

public record BulkScenarioRequest(
        int icuPatients,
        int admittedPatients,
        int registeredPatients,
        String testRunId
) {
}
