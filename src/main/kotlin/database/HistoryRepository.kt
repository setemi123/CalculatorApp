package database

import java.sql.Connection
import java.sql.DriverManager
import java.time.Instant

data class CalculationRecord(
    val id: Int = 0,
    val expression: String,
    val result: Double,
    val createdAt: String = Instant.now().toString()
)

interface HistoryRepository {

    fun add(record: CalculationRecord): CalculationRecord

    fun recent(limit: Int = 20): List<CalculationRecord>

    fun clear(): Int
    fun count(): Int
}



class SqliteHistoryRepository(dbPath: String = "calculator_history.db") : HistoryRepository {

    private val url = "jdbc:sqlite:$dbPath"

    init {
        connect().use { conn ->
            conn.createStatement().use { stmt ->
                stmt.execute(
                    """
                    CREATE TABLE IF NOT EXISTS calculation_history (
                        id          INTEGER PRIMARY KEY AUTOINCREMENT,
                        expression  TEXT    NOT NULL,
                        result      REAL    NOT NULL,
                        created_at  TEXT    NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }
    }

    private fun connect(): Connection = DriverManager.getConnection(url)

    override fun add(record: CalculationRecord): CalculationRecord {
        connect().use { conn ->
            val sql = "INSERT INTO calculation_history (expression, result, created_at) VALUES (?, ?, ?)"
            conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS).use { ps ->
                ps.setString(1, record.expression)
                ps.setDouble(2, record.result)
                ps.setString(3, record.createdAt)
                ps.executeUpdate()

                val generatedId = ps.generatedKeys.use { keys ->
                    if (keys.next()) keys.getInt(1) else 0
                }
                return record.copy(id = generatedId)
            }
        }
    }

    override fun recent(limit: Int): List<CalculationRecord> {
        connect().use { conn ->
            val sql = "SELECT id, expression, result, created_at FROM calculation_history ORDER BY id DESC LIMIT ?"
            conn.prepareStatement(sql).use { ps ->
                ps.setInt(1, limit)
                ps.executeQuery().use { rs ->
                    val records = mutableListOf<CalculationRecord>()
                    while (rs.next()) {
                        records.add(
                            CalculationRecord(
                                id = rs.getInt("id"),
                                expression = rs.getString("expression"),
                                result = rs.getDouble("result"),
                                createdAt = rs.getString("created_at")
                            )
                        )
                    }
                    return records
                }
            }
        }
    }

    override fun clear(): Int {
        connect().use { conn ->
            conn.createStatement().use { stmt ->
                return stmt.executeUpdate("DELETE FROM calculation_history")
            }
        }
    }

    override fun count(): Int {
        connect().use { conn ->
            conn.createStatement().use { stmt ->
                stmt.executeQuery("SELECT COUNT(*) AS c FROM calculation_history").use { rs ->
                    return if (rs.next()) rs.getInt("c") else 0
                }
            }
        }
    }
}