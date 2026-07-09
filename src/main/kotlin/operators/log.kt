package org.example.operators

import utilities.OperationResult
import kotlin.math.ln


fun logarithm(base: Double, value: Double): OperationResult {
    if (value <= 0.0) return OperationResult.Failure(
        "Logarithm is undefined for non-positive values (got $value)."
    )
    if (base <= 0.0) return OperationResult.Failure(
        "Logarithm base must be positive (got $base)."
    )
    if (base == 1.0) return OperationResult.Failure(
        "Logarithm base cannot be 1 (log base 1 is undefined)."
    )
    return OperationResult.Success(ln(value) / ln(base))
}

