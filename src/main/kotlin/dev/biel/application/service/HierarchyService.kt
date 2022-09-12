package dev.biel.application.service

import com.google.gson.Gson
import dev.biel.application.helper.HierarchyHelper
import dev.biel.infrastructure.query.dao
import dev.biel.model.TalentToSupervisorObj

class HierarchyService {
    suspend fun inject(talentToSupervisorMap: Map<String, String>) {
        dao.flush()
        dao.persistAll(talentToSupervisorMap)
    }

    suspend fun fetch(): Map<String, Map<String,Any>> {
        val hierarchyHelper = HierarchyHelper()
        val talentSupervisorList = dao.getAll()

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