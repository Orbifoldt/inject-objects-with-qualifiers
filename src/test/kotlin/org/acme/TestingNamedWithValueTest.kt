package org.acme

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@QuarkusTest
internal class TestingNamedWithValueTest {
    @Test
    fun `injecting a string with @Named("tree") qualifier should return oak`() {
        RestAssured.given()
            .`when`().get("/named-with-value")
            .then()
            .statusCode(200)
            .body(CoreMatchers.`is`("maple"))
    }
}