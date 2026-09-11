package org.example

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import utilities.Operation
import utilities.dispatch

@RestController
@RequestMapping("/v1/calculator")
class CalculatorController {

    @GetMapping("/add")
    fun add(
        @RequestParam a: Double,
        @RequestParam b: Double
    ): Any {
        return dispatch(
            Operation.ADDITION,
            a,
            b
        )
    }

    @GetMapping("/subtract")
    fun subtract(
        @RequestParam a: Double,
        @RequestParam b: Double
    ): Any {
        return dispatch(
            Operation.SUBTRACTION,
            a,
            b
        )
    }

    @GetMapping("/multiply")
    fun multiply(
        @RequestParam a: Double,
        @RequestParam b: Double
    ): Any {
        return dispatch(
            Operation.MULTIPLICATION,
            a,
            b
        )
    }

    @GetMapping("/divide")
    fun divide(
        @RequestParam a: Double,
        @RequestParam b: Double
    ): Any {
        return dispatch(
            Operation.DIVISION,
            a,
            b
        )
    }

    @GetMapping("/modulus")
    fun modulus(
        @RequestParam a: Double,
        @RequestParam b: Double
    ): Any {
        return dispatch(
            Operation.MODULUS,
            a,
            b
        )
    }

    @GetMapping("/power")
    fun power(
        @RequestParam a: Double,
        @RequestParam b: Double
    ): Any {
        return dispatch(
            Operation.EXPONENTIAL,
            a,
            b
        )
    }

    @GetMapping("/sqrt")
    fun squareRoot(
        @RequestParam a: Double
    ): Any {
        return dispatch(
            Operation.SQUARE_ROOT,
            a
        )
    }

    @GetMapping("/cbrt")
    fun cubeRoot(
        @RequestParam a: Double
    ): Any {
        return dispatch(
            Operation.CUBE_ROOT,
            a
        )
    }

    @GetMapping("/root")
    fun root(
        @RequestParam x: Double,
        @RequestParam number: Double
    ): Any {
        return dispatch(
            Operation.ROOT_OF_X,
            x,
            number
        )
    }

    @GetMapping("/log")
    fun logarithm(
        @RequestParam base: Double,
        @RequestParam value: Double
    ): Any {
        return dispatch(
            Operation.LOGARITHM,
            base,
            value
        )
    }
}