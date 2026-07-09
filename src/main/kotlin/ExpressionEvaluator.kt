package org.example

import utilities.Operation
import utilities.OperationResult
import utilities.dispatch
import utilities.isRightAssociative
import utilities.precedence
import java.util.ArrayDeque



sealed class EvalResult {
    data class Success(val value: Double) : EvalResult()
    data class Failure(val message: String) : EvalResult()
}

private const val SUPPORTED_OPERATORS = "+-*/%^"



private fun tokenize(expression: String): Result<List<String>> {
    val tokens = mutableListOf<String>()
    val numberBuffer = StringBuilder()

    fun flushNumber(): String? {
        if (numberBuffer.isEmpty()) return null
        val value = numberBuffer.toString()
        numberBuffer.clear()
        return value
    }

    var i = 0
    while (i < expression.length) {
        val c = expression[i]
        when {
            c.isWhitespace() -> {
                flushNumber()?.let { tokens.add(it) }
            }
            c.isDigit() || c == '.' -> {
                numberBuffer.append(c)
            }
            // Handle a leading unary minus, e.g. "-3 + 5" or "(-3 + 5)"
            c == '-' && numberBuffer.isEmpty() &&
                    (tokens.isEmpty() || tokens.last() in listOf("(", "+", "-", "*", "/", "%", "^")) -> {
                numberBuffer.append(c)
            }
            c in SUPPORTED_OPERATORS -> {
                flushNumber()?.let { tokens.add(it) }
                tokens.add(c.toString())
            }
            c == '(' || c == ')' -> {
                flushNumber()?.let { tokens.add(it) }
                tokens.add(c.toString())
            }
            else -> return Result.failure(
                IllegalArgumentException("Unrecognised character '$c' in expression.")
            )
        }
        i++
    }
    flushNumber()?.let { tokens.add(it) }

    if (tokens.isEmpty()) {
        return Result.failure(IllegalArgumentException("Expression is empty."))
    }
    return Result.success(tokens)
}



private fun evaluateTokens(tokens: List<String>): EvalResult {
    val values = ArrayDeque<Double>()
    val operators = ArrayDeque<Char>()

    fun applyTop(): String? {
        if (operators.isEmpty()) return "No operator to apply."
        val opChar = operators.pop()
        if (values.size < 2) return "Malformed expression near '$opChar'."

        val b = values.pop()
        val a = values.pop()
        val operation = Operation.fromSymbol(opChar)
            ?: return "Unsupported operator '$opChar'."

        return when (val result = dispatch(operation, a, b)) {
            is OperationResult.Success -> {
                values.push(result.value)
                null
            }
            is OperationResult.Failure -> result.message
            else -> "Unknown operation result"
        }
    }

    for (token in tokens) {
        when {
            token.toDoubleOrNull() != null -> values.push(token.toDouble())

            token == "(" -> operators.push('(')

            token == ")" -> {
                while (operators.isNotEmpty() && operators.peek() != '(') {
                    applyTop()?.let { return EvalResult.Failure(it) }
                }
                if (operators.isEmpty()) {
                    return EvalResult.Failure("Mismatched parentheses: missing '('.")
                }
                operators.pop() // discard the '('
            }

            token.length == 1 && token[0] in SUPPORTED_OPERATORS -> {
                val opChar = token[0]
                while (
                    operators.isNotEmpty() &&
                    operators.peek() != '(' &&
                    (
                            precedence(operators.peek()) > precedence(opChar) ||
                                    (precedence(operators.peek()) == precedence(opChar) && !isRightAssociative(opChar))
                            )
                ) {
                    applyTop()?.let { return EvalResult.Failure(it) }
                }
                operators.push(opChar)
            }

            else -> return EvalResult.Failure("Invalid token '$token' in expression.")
        }
    }

    while (operators.isNotEmpty()) {
        if (operators.peek() == '(') {
            return EvalResult.Failure("Mismatched parentheses: missing ')'.")
        }
        applyTop()?.let { return EvalResult.Failure(it) }
    }

    if (values.size != 1) {
        return EvalResult.Failure("Malformed expression — could not reduce to a single result.")
    }
    return EvalResult.Success(values.pop())
}


fun evaluateExpression(expression: String): EvalResult {
    val trimmed = expression.trim()
    if (trimmed.isEmpty()) return EvalResult.Failure("Please enter an expression.")

    val tokenResult = tokenize(trimmed)
    val tokens = tokenResult.getOrElse {
        return EvalResult.Failure(it.message ?: "Could not parse expression.")
    }

    return try {
        evaluateTokens(tokens)
    } catch (e: Exception) {


        EvalResult.Failure("Could not evaluate expression: ${e.message ?: "unknown error"}")
    }
}

