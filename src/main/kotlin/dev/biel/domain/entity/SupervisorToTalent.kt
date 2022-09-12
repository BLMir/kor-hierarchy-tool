package dev.bl.domain.entity

import org.jetbrains.exposed.sql.Table

object SupervisorToTalent : Table() {
    val supervisor = varchar("supervisor", 50)// Column<String>
    val talent = varchar("talent", 50) // Column<String>

    override val primaryKey = PrimaryKey(talent, name = "PK_ReportingLine_ID")
}