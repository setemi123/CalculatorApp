package utilities

import org.example.operators.add
import org.example.operators.cubeRoot
import org.example.operators.divide
import org.example.operators.exponential
import org.example.operators.logarithm
import org.example.operators.modulus
import org.example.operators.multiply
import org.example.operators.rootOfX
import org.example.operators.squareRoot
import org.example.operators.subtract



fun dispatch(
    operation: Operation,
    a: Double,
    b: Double = 0.0,
    operationFn: (Operation, Double, Double) -> OperationResult = ::defaultDispatch
): OperationResult = operationFn(operation, a, b)


fun defaultDispatch(operation: Operation, a: Double, b: Double): OperationResult =
    when (operation) {
        Operation.ADDITION       -> add(a, b)
        Operation.SUBTRACTION    -> subtract(a, b)
        Operation.MULTIPLICATION -> multiply(a, b)
        Operation.DIVISION       -> divide(a, b)
        Operation.MODULUS        -> modulus(a, b)
        Operation.SQUARE_ROOT    -> squareRoot(a)
        Operation.CUBE_ROOT      -> cubeRoot(a)
        Operation.ROOT_OF_X      -> rootOfX(a, b)   // a = index/root, b = radicand
        Operation.EXPONENTIAL    -> exponential(a, b)
        Operation.LOGARITHM      -> logarithm(a, b) // a = base, b = value
    }
