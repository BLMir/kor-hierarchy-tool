package dev.biel.presentation.controller

import dev.biel.application.service.TalentService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.talentController(){
    val talentService by inject<TalentService> ()

    route("talent"){
        get("{name}/supervisor"){
            val talentName = call.parameters["name"] ?: return@get call.respondText(
                "Missing name",
                status = HttpStatusCode.BadRequest
            )

            call.respondText(
                text = talentService.getSupervisors(talentName).toString(),
                status = HttpStatusCode.Accepted
            )
        }
    }
}