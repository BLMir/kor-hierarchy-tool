package dev.biel.presentation.controller

import dev.biel.application.service.InjectHierarchyService
import dev.biel.application.service.ValidationService
import dev.biel.infrastructure.query.dao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.hierarchyController(){
    val injectService by inject<InjectHierarchyService> ()

    route("/hierarchy") {
        post {
            try {
                val request = call.receive<Map<String,String>>()
                ValidationService().start(request)

                injectService.inject(request)

                call.respondText(
                    status = HttpStatusCode.Created,
                    text = dao.getAll().toString()
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