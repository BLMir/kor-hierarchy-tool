package dev.biel.application.service

import dev.biel.application.helper.HierarchyHelper
import dev.biel.infrastructure.query.TalentToSupervisorDAO
import dev.biel.model.TalentToSupervisorObj

class HierarchyService {
    suspend fun inject(talentToSupervisorMap: Map<String, String>) {
        TalentToSupervisorDAO.flush()
        TalentToSupervisorDAO.persistAll(talentToSupervisorMap)
    }

    suspend fun fetch(): Map<String, Map<String,Any>> {
        val hierarchyHelper = HierarchyHelper()
        val talentSupervisorList = TalentToSupervisorDAO.getAll()

        talentSupervisorList.toSupervisorToTalentsList(hierarchyHelper)

        val root = hierarchyHelper.getRoot(talentSupervisorList)
        val orderElementList = mutableSetOf<String>()

        hierarchyHelper.ToFlatOrder(root,orderElementList)

        val result = hierarchyHelper.transformToNestedMap(orderElementList)
        val rootValue = result[root] ?: mapOf()

        return mapOf(root to rootValue)
    }

    private fun List<TalentToSupervisorObj>.toSupervisorToTalentsList(hierarchyHelper: HierarchyHelper) = this.forEach{ talentToSupervisor ->
        hierarchyHelper.addIntoMutableList(talentToSupervisor.supervisor, talentToSupervisor.talent)
    }
}