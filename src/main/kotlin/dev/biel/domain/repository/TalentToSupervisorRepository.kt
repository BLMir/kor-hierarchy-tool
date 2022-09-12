package dev.biel.domain.repository

import dev.biel.model.TalentToSupervisorObj

interface TalentToSupervisorRepository {
    suspend fun persistAll(talentToSupervisorMap: Map<String,String>): Unit
    suspend fun getAll():List<TalentToSupervisorObj>
    suspend fun flush(): Unit
    suspend fun getByTalent(talent: String):TalentToSupervisorObj?
}