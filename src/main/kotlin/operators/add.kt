package org.example.operators

import utilities.OperationResult



fun add(a: Double, b: Double): OperationResult =
    OperationResult.Success(a + b)

