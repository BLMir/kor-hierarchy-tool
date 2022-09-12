package dev.biel.presentation.controller

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TalentControllerTest {

    @Test
    fun testTalent() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        environment {
            config = ApplicationConfig("application.conf")
        }

        //preparing database
        client.post("/hierarchy") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Basic YWRtaW46YWRtaW4=")
            setBody(mapOf(
                "Pete" to "Nick",
                "Barbara" to "Nick",
                "Biel" to "Barbara",
                "Pedro" to "Pete",
                "Pila" to "Biel",
                "Nick" to "Jonas"))
        }

        val response = client.get("/talent/Nick/supervisor") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Basic YWRtaW46YWRtaW4=")
        }

        assertEquals(response.bodyAsText(), setOf("Jonas").toString())

        val response2Supervisors = client.get("/talent/Biel/supervisor") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Basic YWRtaW46YWRtaW4=")
        }

        assertEquals(response2Supervisors.bodyAsText(), setOf("Barbara","Nick").toString())

    }
}