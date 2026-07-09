package org.example.operators

import utilities.OperationResult
import utilities.formatOrdinal
import kotlin.math.abs
import kotlin.math.pow


fun rootOfX(x: Double, a: Double): OperationResult {
    if (x == 0.0) return OperationResult.Failure(
        "The zeroth root is undefined."
    )


    if (a < 0.0) {
        val isOddInteger = (x == kotlin.math.floor(x)) && (x.toLong() % 2L != 0L)
        if (!isOddInteger) return OperationResult.Failure(
            "Cannot compute the ${formatOrdinal(x)} root of a negative number ($a). " +
                    "The result would be complex."
        )

        val positiveResult = abs(a).pow(1.0 / x)
        return OperationResult.Success(-positiveResult)
    }

    return OperationResult.Success(a.pow(1.0 / x))
}