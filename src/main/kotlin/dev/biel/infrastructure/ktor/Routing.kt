package dev.biel.infrastructure.ktor

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*

fun Application.configureRouting() {
    routing {
        authenticate("auth-basic") {
            get("/") {
                call.respondText("Hello World!")
            }
        }
    }
}
