package dev.biel.infrastructure.ktor

import dev.biel.application.service.InjectHierarchyService
import io.ktor.server.application.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

val hierarchyModule = module {
    singleOf (::InjectHierarchyService)
}

fun Application.configDependencyInjection() {
    install(Koin) {
        slf4jLogger()
        modules(hierarchyModule)
    }
}