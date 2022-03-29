package org.acme

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@QuarkusTest
internal class TestingQualifiersTest {
    @Test
    fun `injecting a string with @Trees qualifier should return oak`() {
        RestAssured.given()
            .`when`().get("/qualified")
            .then()
            .statusCode(200)
            .body(CoreMatchers.`is`("oak"))
    }
}