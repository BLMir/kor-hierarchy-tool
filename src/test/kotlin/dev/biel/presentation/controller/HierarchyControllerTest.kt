package dev.biel.presentation.controller

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.server.testing.*
import kotlin.test.*

class HierarchyControllerTest {

    @Test
    fun testHierarchy() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        environment {
            config = ApplicationConfig("application.conf")
        }

        val response = client.post("/hierarchy") {
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

        assertTrue(response.bodyAsText().contains("Nick"))
        assertEquals(response.status, HttpStatusCode.Created)
        assertTrue(response.bodyAsText().contains("\"Jonas\":{\"Nick\":{\"Pete\":{\"Pedro\":{}},\"Barbara\":{\"Biel\":{\"Pila\":{}}}}}"))

        val responseBadJsonRequest = client.post("/hierarchy") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Basic YWRtaW46YWRtaW4=")
            setBody(mapOf(
                "Pete" to "Nick",
                "Barbara" to "Nick",
                "Biel" to "Barbara",
                "Pedro" to "Pete",
                "Pila" to "Biel",
                "Nick" to "Jonas",
                "Caty" to "ceo2"))
        }

        assertEquals(responseBadJsonRequest.status, HttpStatusCode.BadRequest)
        assertTrue(responseBadJsonRequest.bodyAsText().contains("There are more than one root (CEO)"))

        val responseBadJsonRequestInfinityLoop = client.post("/hierarchy") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Basic YWRtaW46YWRtaW4=")
            setBody(mapOf(
                "Pete" to "Nick",
                "Barbara" to "Nick",
                "Biel" to "Barbara",
                "Pedro" to "Pete",
                "Pila" to "Biel",
                "Nick" to "Jonas",
                "Jonas" to "Biel"))
        }

        assertEquals(responseBadJsonRequestInfinityLoop.status, HttpStatusCode.BadRequest)
        assertTrue(responseBadJsonRequestInfinityLoop.bodyAsText().contains("There is an infinite loop"))
    }
}