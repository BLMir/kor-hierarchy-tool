package dev.biel.domain.repository

import dev.biel.domain.entity.TalentToSupervisor

interface TalentToSupervisorRepository {
    suspend fun persistAll(talentToSupervisorMap: Map<String,String>): Unit
    suspend fun getAll():List<TalentToSupervisor>
    suspend fun flush(): Unit
}