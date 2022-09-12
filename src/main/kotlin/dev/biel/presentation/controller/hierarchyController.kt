package dev.biel.presentation.controller

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

            } catch (e:Exception) {
                call.respondText(status = HttpStatusCode.BadRequest, text = e.message ?: "no specific error message")
            }
        }
    }
}