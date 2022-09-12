package dev.biel.presentation.controller

import dev.biel.application.service.HierarchyService
import dev.biel.application.service.ValidationService
import dev.biel.infrastructure.query.dao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.hierarchyController(){
    val hierarchyService by inject<HierarchyService> ()

    route("/hierarchy") {
        post {
            try {
                val request = call.receive<Map<String,String>>()
                ValidationService().start(request)

                hierarchyService.inject(request)
                val jsonResult = hierarchyService.fetch()
                call.respondText(
                    status = HttpStatusCode.Created,
                    text = jsonResult,
                    contentType = ContentType("application","json")
                )
            } catch (e:Exception) {
                call.respondText(
                    status = HttpStatusCode.BadRequest,
                    text = e.message ?: "no specific error message",
                    contentType = ContentType("application","json")
                )
            }
        }
    }
}