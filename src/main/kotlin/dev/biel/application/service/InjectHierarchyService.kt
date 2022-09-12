package dev.biel.application.service

import dev.biel.infrastructure.query.dao

class InjectHierarchyService {

    suspend fun inject(talentToSupervisorMap: Map<String, String>) {
        dao.flush()
        dao.persistAll(talentToSupervisorMap)

        println("inject")
    }
}