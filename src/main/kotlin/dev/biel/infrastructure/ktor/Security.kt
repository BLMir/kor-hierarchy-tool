package dev.biel.infrastructure.ktor

import io.ktor.server.auth.*
import io.ktor.server.application.*
import io.ktor.server.config.*

fun Application.configureSecurity(config: ApplicationConfig) {
	val username = config.property("ktor.access.user").getString()
	val pass = config.property("ktor.access.pass").getString()

	install(Authentication) {
		basic("auth-basic") {
			realm = "Access to the '/' path"
			validate { credentials ->
				if (credentials.name == username && credentials.password == pass) {
					UserIdPrincipal(credentials.name)
				} else {
					null
				}
			}
		}
	}
}
