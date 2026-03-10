package com.testdata.tests;

import com.testdata.PatientDataGenerateApplication;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(
        classes = PatientDataGenerateApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestDataServiceTest extends AbstractTestNGSpringContextTests {

    @LocalServerPort
    private int port;

    private String patientId;
    private String ssn;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:" + port + "/testdata";

        given()
                .when()
                .delete("/cleanup/patients")
                .then()
                .statusCode(204);
    }

    @Test(priority = 1)
    public void testCreateRegisteredPatient() {
        Response response =
                given()
                        .when()
                        .post("/patients/registered")
                        .then()
                        .statusCode(200)
                        .body("patientId", notNullValue())
                        .body("ssn", notNullValue())
                        .extract().response();

        patientId = response.jsonPath().getString("patientId");
        ssn = response.jsonPath().getString("ssn");

        Assert.assertNotNull(patientId);
        Assert.assertNotNull(ssn);
    }

    @Test(priority = 2)
    public void testCreateAdmittedPatient() {
        given()
                .when()
                .post("/patients/admitted")
                .then()
                .statusCode(200)
                .body("status", equalTo("ADMITTED"));
    }

    @Test(priority = 3)
    public void testCreateICUPatient() {
        given()
                .when()
                .post("/patients/icu")
                .then()
                .statusCode(200)
                .body("status", equalTo("ICU"));
    }

    @Test(priority = 4)
    public void testBulkPatientCreation() {
        Response response =
                given()
                        .queryParam("count", 10)
                        .when()
                        .post("/patients/bulk")
                        .then()
                        .statusCode(200)
                        .extract().response();

        Assert.assertEquals(response.jsonPath().getList("$").size(), 10);
    }

    @Test(priority = 5)
    public void testScenarioApis() {
        given()
                .when()
                .post("/scenarios/full-admission")
                .then()
                .statusCode(200)
                .body("patientId", notNullValue())
                .body("doctorId", notNullValue())
                .body("status", equalTo("ICU"));

        Map<String, Object> payload = new HashMap<>();
        payload.put("registeredPatients", 2);
        payload.put("admittedPatients", 2);
        payload.put("icuPatients", 1);
        payload.put("testRunId", "it-run");

        Response bulkScenarioResponse =
                given()
                        .contentType("application/json")
                        .body(payload)
                        .when()
                        .post("/scenarios/bulk")
                        .then()
                        .statusCode(200)
                        .extract().response();

        Assert.assertEquals(bulkScenarioResponse.jsonPath().getList("$").size(), 5);
    }

    @Test(priority = 6)
    public void testGetPatientsAndValidation() {
        Response response =
                given()
                        .when()
                        .get("/patients")
                        .then()
                        .statusCode(200)
                        .extract().response();

        List<Map<String, Object>> patients = response.jsonPath().getList("$");

        Assert.assertTrue(patients.size() > 0);
        Assert.assertTrue(patients.stream().allMatch(p -> p.get("patientId") != null));
        Assert.assertTrue(patients.stream().allMatch(p -> p.get("ssn") != null));
        Assert.assertTrue(patients.stream().allMatch(p -> p.get("status") != null));
    }

    @Test(priority = 7)
    public void testStatsAPI() {
        Response response =
                given()
                        .when()
                        .get("/stats")
                        .then()
                        .statusCode(200)
                        .extract().response();

        int patientsCreated = response.jsonPath().getInt("patients");
        int ssnTotal = response.jsonPath().getInt("ssnTotal");
        int ssnUsed = response.jsonPath().getInt("ssnUsed");

        Assert.assertTrue(patientsCreated >= 1);
        Assert.assertTrue(ssnTotal >= 1000);
        Assert.assertTrue(ssnUsed >= patientsCreated);
    }

    @Test(priority = 8)
    public void testCleanupPatients() {
        given()
                .when()
                .delete("/cleanup/patients")
                .then()
                .statusCode(204);
    }

    @Test(priority = 9)
    public void testPatientsAfterCleanup() {
        Response response =
                given()
                        .when()
                        .get("/patients")
                        .then()
                        .statusCode(200)
                        .extract().response();

        int size = response.jsonPath().getList("$").size();

        Assert.assertEquals(size, 0);
    }
}
