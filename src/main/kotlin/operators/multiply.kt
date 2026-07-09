package org.example.operators

import utilities.OperationResult


fun multiply(a: Double, b: Double): OperationResult =
    OperationResult.Success(a * b)