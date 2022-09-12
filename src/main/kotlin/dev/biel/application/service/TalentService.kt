package dev.biel.application.service

import dev.biel.infrastructure.query.TalentToSupervisorDAO

class TalentService {
    private val level = 2

     suspend fun getSupervisors(talent: String): MutableSet<String?> {
         val supervisorsList = mutableSetOf<String?>()
         var supervisor = talent
         repeat(level){
            supervisor = TalentToSupervisorDAO.getByTalent(supervisor)?.supervisor ?: return@repeat
            supervisorsList.add(supervisor)
         }

        return supervisorsList
     }
}