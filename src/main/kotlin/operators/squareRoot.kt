package org.example.operators


import utilities.OperationResult
import kotlin.math.sqrt

fun squareRoot(a: Double): OperationResult {
    if (a < 0.0) return OperationResult.Failure(
        "Cannot take the square root of a negative number ($a). " +
                "Result would be complex."
    )
    return OperationResult.Success(sqrt(a))
}


