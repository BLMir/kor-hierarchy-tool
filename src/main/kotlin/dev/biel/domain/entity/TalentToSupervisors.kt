package dev.biel.domain.entity

import org.jetbrains.exposed.sql.Table

object TalentToSupervisors : Table() {
    val talent = varchar("talent", 50) // Column<String>
    val supervisor = varchar("supervisor", 50)// Column<String>

    override val primaryKey = PrimaryKey(talent, name = "PK_ReportingLine_ID")
}