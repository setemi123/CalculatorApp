package org.example.operators

import utilities.OperationResult


fun subtract(a: Double, b: Double): OperationResult =
    OperationResult.Success(a - b)
