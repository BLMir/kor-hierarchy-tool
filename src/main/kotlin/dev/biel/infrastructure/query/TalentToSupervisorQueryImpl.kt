package dev.biel.infrastructure.query

import dev.biel.domain.entity.TalentToSupervisors
import dev.biel.domain.repository.TalentToSupervisorRepository
import dev.biel.infrastructure.ktor.DatabaseFactory.dbQuery
import dev.biel.model.TalentToSupervisorObj
import org.jetbrains.exposed.sql.*

class TalentToSupervisorQueryImpl: TalentToSupervisorRepository {
    override suspend fun persistAll(reportingLineList: Map<String, String>) {
        dbQuery {
            TalentToSupervisors.batchInsert(reportingLineList.toList()) { row ->
                this[TalentToSupervisors.talent] = row.first
                this[TalentToSupervisors.supervisor] = row.second
            }
        }
    }

    override suspend fun getAll(): List<TalentToSupervisorObj> = dbQuery {
        TalentToSupervisors.selectAll().map(::rowToReportingLine)
    }

    override suspend fun flush(): Unit = dbQuery {
        TalentToSupervisors.deleteAll()
    }

    override suspend fun getByTalent(talent: String): TalentToSupervisorObj? = dbQuery {
        TalentToSupervisors.select { TalentToSupervisors.talent eq talent }.map(::rowToReportingLine).singleOrNull()

    }

    private fun rowToReportingLine(row: ResultRow) = TalentToSupervisorObj(
        talent = row[TalentToSupervisors.talent],
        supervisor = row[TalentToSupervisors.supervisor],
    )
}

val TalentToSupervisorDAO: TalentToSupervisorRepository = TalentToSupervisorQueryImpl()