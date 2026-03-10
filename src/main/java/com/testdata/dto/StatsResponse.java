package com.testdata.dto;

public record StatsResponse(
        long patients,
        long ssnTotal,
        long ssnUsed,
        long ssnAvailable
) {
}
