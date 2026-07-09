package org.example.operators


import utilities.OperationResult


fun modulus(a: Double, b: Double): OperationResult {
    if (b == 0.0) return OperationResult.Failure("Cannot compute modulus with a divisor of zero.")
    return OperationResult.Success(a % b)
}