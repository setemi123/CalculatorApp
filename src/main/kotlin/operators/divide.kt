package org.example.operators

import utilities.OperationResult


fun divide(a: Double, b: Double): OperationResult {
    if (b == 0.0) return OperationResult.Failure("Cannot divide by zero.")
    return OperationResult.Success(a / b)
}