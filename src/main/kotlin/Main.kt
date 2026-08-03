package org.example

import database.CalculationRecord
import database.SqliteHistoryRepository
import utilities.Operation
import utilities.OperationResult
import utilities.dispatch
import utilities.formatExpression
import utilities.formatResult

fun main() {
    showHowTo()

    val repository = SqliteHistoryRepository()

    println("Terminal Calculator — type an expression (e.g. 3 + 4 * 2),")
    println("a unary command (SQRT 9, CBRT 27, LOG 2 8, ROOTX 3 27),")
    println("or HISTORY / CLEAR HISTORY / EXIT.\n")

    while (true) {
        print("> ")
        val input = readlnOrNull()?.trim()

        if (input.isNullOrEmpty()) {
            continue
        }

        when {
            input.equals("EXIT", ignoreCase = true) || input.equals("QUIT", ignoreCase = true) -> {
                println("Goodbye!")
                return
            }

            input.equals("HISTORY", ignoreCase = true) -> {
                viewHistory(repository)
            }

            input.equals("CLEAR HISTORY", ignoreCase = true) -> {
                clearHistory(repository)
            }

            input.equals("HELP", ignoreCase = true) -> {
                showHowTo()
            }

            isUnaryOrNamedCommand(input) -> {
                handleNamedCommand(input, repository)
            }

            else -> {
                handleExpression(input, repository)
            }
        }
    }
}


private fun isUnaryOrNamedCommand(input: String): Boolean {
    val firstWord = input.split(Regex("\\s+")).firstOrNull() ?: return false
    val op = Operation.fromCommand(firstWord) ?: return false
    return !op.isBinary || op == Operation.ROOT_OF_X || op == Operation.LOGARITHM
}

private fun handleNamedCommand(input: String, repository: SqliteHistoryRepository) {
    val parts = input.split(Regex("\\s+"))
    val operation = Operation.fromCommand(parts[0])

    if (operation == null) {
        println("Unknown operation '${parts[0]}'. Type HELP to see available commands.")
        return
    }

    val operandsNeeded = if (operation.isBinary) 2 else 1
    val operandTokens = parts.drop(1)

    if (operandTokens.size < operandsNeeded) {
        println("'${operation.command}' needs $operandsNeeded operand(s). Example: ${exampleFor(operation)}")
        return
    }

    val a = operandTokens[0].toDoubleOrNull()
    val bRaw = if (operandsNeeded == 2) operandTokens[1].toDoubleOrNull() else 0.0

    if (a == null || bRaw == null) {
        println("Could not parse operand(s) as a number. Example: ${exampleFor(operation)}")
        return
    }
    val b = bRaw

    when (val result = dispatch(operation, a, b)) {
        is OperationResult.Success -> {
            val expressionText = formatExpression(operation, a, b, result.value)
            println(expressionText)
            persist(repository, expressionText.substringBefore(" ="), result.value)
        }
        is OperationResult.Failure -> {
            println("Error: ${result.message}")
        }
    }
}

private fun exampleFor(operation: Operation): String = when (operation) {
    Operation.SQUARE_ROOT -> "SQRT 9"
    Operation.CUBE_ROOT   -> "CBRT 27"
    Operation.ROOT_OF_X   -> "ROOTX 3 27   (3rd root of 27)"
    Operation.LOGARITHM   -> "LOG 2 8      (log base 2 of 8)"
    else                  -> "${operation.command} <a> <b>"
}

private fun handleExpression(input: String, repository: SqliteHistoryRepository) {
    when (val result = evaluateExpression(input)) {
        is EvalResult.Success -> {
            println("${input.trim()} = ${formatResult(result.value)}")
            persist(repository, input.trim(), result.value)
        }
        is EvalResult.Failure -> {
            println("Error: ${result.message}")
        }
    }
}

private fun persist(repository: SqliteHistoryRepository, expression: String, result: Double) {
    runCatching {
        repository.add(CalculationRecord(expression = expression, result = result))
    }.onFailure {

        println("(Note: could not save this calculation to history: ${it.message})")
    }
}

