package org.example.operators

import utilities.OperationResult
import kotlin.math.pow


fun exponential(a: Double, b: Double): OperationResult {
    if (a < 0.0 && b != kotlin.math.floor(b)) {
        return OperationResult.Failure(
            "Cannot raise a negative base ($a) to a fractional exponent ($b). " +
                    "The result would be complex."
        )
    }
    return OperationResult.Success(a.pow(b))
}