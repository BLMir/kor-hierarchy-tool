package dev.biel.presentation.controller

import dev.biel.application.service.ValidationService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.hierarchyController(){
    route("/hierarchy") {
        post {
            try {
                val request = call.receive<Map<String,String>>()

                ValidationService().start(request)

                call.respondText(
                    status = HttpStatusCode.Created,
                    text = "validated"
                )
            } catch (e:Exception) {
                call.respondText(
                    status = HttpStatusCode.BadRequest,
                    text = e.message ?: "no specific error message"
                )
            }
        }
    }
}