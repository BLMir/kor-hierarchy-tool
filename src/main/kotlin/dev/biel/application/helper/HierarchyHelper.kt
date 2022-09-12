package dev.biel.application.helper

import dev.biel.application.common.getRoot
import dev.biel.model.TalentToSupervisorObj
import java.util.ArrayDeque

class HierarchyHelper {
    val supervisorToTalentList: MutableMap<String, MutableSet<String>> = LinkedHashMap()

    fun addIntoMutableList(supervisor: String, talent: String) {

        val currentTalentsList = if(supervisorToTalentList[supervisor].isNullOrEmpty()){
            mutableSetOf()
        }else {
            supervisorToTalentList[supervisor]
        }
        currentTalentsList!!.add(talent)

        supervisorToTalentList[supervisor] = currentTalentsList
    }

    fun getRoot(talentToSupervisor: List<TalentToSupervisorObj>) =
        talentToSupervisor.map { it.talent to it.supervisor }.toMap().getRoot().first()

    fun ToFlatOrder(supervisor: String, childTalentList: MutableSet<String>) {
        childTalentList.add(supervisor)
        for (sub in supervisorToTalentList[supervisor] ?: setOf()) {
            ToFlatOrder(sub, childTalentList)
        }
    }

    private fun twoLevelsNestedElements(): MutableMap<String, MutableMap<String, Any>> {
        val nestedMap = mutableMapOf<String, MutableMap<String, Any>>()
        for (supervisor in supervisorToTalentList.keys) {
            val childList = mutableMapOf<String, Any>()
            supervisorToTalentList[supervisor]?.forEach { childList[it] = emptyMap<String, Any>() }
            nestedMap[supervisor] = childList
        }
        return nestedMap
    }

    fun transformToNestedMap(orderElementList: MutableSet<String>): Map<String, Map<String, Any>> {
        val nestedMap = twoLevelsNestedElements()
        orderElementList.forEach { talent ->
            val supervisor = nestedMap.filterValues {
                it.containsKey(talent.toString())
            }.keys.toList()
            if (supervisor.isNotEmpty()) {
                val supervisorName = supervisor.first().toString()
                val talentList = nestedMap[supervisorName] ?: mutableMapOf()
                talentList[talent.toString()] = nestedMap[talent.toString()] ?: mutableMapOf<String, MutableMap<String, Any>>()
                nestedMap[supervisorName] = talentList
            }
        }
        return nestedMap
    }
}