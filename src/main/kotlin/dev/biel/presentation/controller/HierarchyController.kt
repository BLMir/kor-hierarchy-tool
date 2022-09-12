package dev.biel.presentation.controller

import com.google.gson.Gson
import dev.biel.application.service.HierarchyService
import dev.biel.application.service.ValidationService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.hierarchyController(){
    val hierarchyService by inject<HierarchyService> ()

    route("hierarchy") {
        post {
            try {
                val request = call.receive<Map<String,String>>()
                ValidationService().start(request)

                hierarchyService.inject(request)
                val gson = Gson()
                val jsonResult = hierarchyService.fetch()
                call.respondText(
                    status = HttpStatusCode.Created,
                    text = gson.toJson(jsonResult),
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