package dev.biel.infrastructure.ktor

import dev.biel.presentation.controller.hierarchyController
import dev.biel.presentation.controller.talentController
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureRouting() {
    routing {
        authenticate("auth-basic") {
            hierarchyController()
            talentController()
        }
    }
}
