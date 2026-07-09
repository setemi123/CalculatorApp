package org.example

import database.HistoryRepository


fun clearHistory(repository: HistoryRepository) {
    print("Are you sure you want to clear all history? [y/N]: ")
    val answer = readlnOrNull()?.trim()?.lowercase()

    if (answer == "y" || answer == "yes") {
        val removed = repository.clear()
        println("Cleared $removed record(s) from history.")
    } else {
        println("Cancelled. History was not modified.")
    }
}

