package dev.biel.infrastructure.query

import dev.biel.domain.entity.TalentToSupervisors
import dev.biel.domain.repository.TalentToSupervisorRepository
import dev.biel.infrastructure.DatabaseFactory
import dev.biel.model.TalentToSupervisorObj
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.selectAll

class TalentToSupervisorQueryImpl: TalentToSupervisorRepository {
    override suspend fun persistAll(reportingLineList: Map<String, String>) {
        DatabaseFactory.dbQuery {
            TalentToSupervisors.batchInsert(reportingLineList.toList()) { row ->
                this[TalentToSupervisors.talent] = row.first
                this[TalentToSupervisors.supervisor] = row.second
            }
        }
    }

    override suspend fun getAll(): List<TalentToSupervisorObj> = DatabaseFactory.dbQuery {
        TalentToSupervisors.selectAll().map(::rowToReportingLine)
    }

    override suspend fun flush(): Unit = DatabaseFactory.dbQuery {
        TalentToSupervisors.deleteAll()
    }

    private fun rowToReportingLine(row: ResultRow) = TalentToSupervisorObj(
        talent = row[TalentToSupervisors.talent],
        supervisor = row[TalentToSupervisors.supervisor],
    )
}

val dao: TalentToSupervisorRepository = TalentToSupervisorQueryImpl()