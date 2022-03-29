package org.acme

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@QuarkusTest
internal class TestingNamedTest {
    @Test
    fun `injecting a string with @Named("someFruit") qualifier should return grape`() {
        RestAssured.given()
            .`when`().get("/named")
            .then()
            .statusCode(200)
            .body(CoreMatchers.`is`("grape"))
    }
}