package org.example

import database.HistoryRepository
import utilities.formatResult


fun viewHistory(repository: HistoryRepository, limit: Int = 20) {
    val records = repository.recent(limit)

    if (records.isEmpty()) {
        println("No history yet. Try a calculation first!")
        return
    }

    println()
    println("Last ${records.size} calculation(s):")
    println("-".repeat(60))
    println("%-4s %-30s %-15s %-19s".format("ID", "Expression", "Result", "When"))
    println("-".repeat(60))
    for (record in records) {
        println(
            "%-4d %-30s %-15s %-19s".format(
                record.id,
                record.expression,
                formatResult(record.result),
                record.createdAt.take(19).replace("T", " ")
            )
        )
    }
    println("-".repeat(60))
    println()
}
