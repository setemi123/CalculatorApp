package org.example.operators

import utilities.OperationResult


fun cubeRoot(a: Double): OperationResult {

    return OperationResult.Success(Math.cbrt(a))
}