package utilities


fun precedence(op: Char): Int =
    when (op) {
        '+', '-' -> 1
        '*', '/', '%' -> 2
        '^' -> 3
        else -> 0
    }


fun isRightAssociative(op: Char): Boolean = op == '^'



fun formatResult(value: Double): String =
    if (value == kotlin.math.floor(value) && !value.isInfinite())
        value.toLong().toString()
    else
        value.toBigDecimal().stripTrailingZeros().toPlainString()



fun formatExpression(operation: Operation, a: Double, b: Double, result: Double): String {
    val res = formatResult(result)
    return when (operation) {
        Operation.ADDITION       -> "${formatResult(a)} + ${formatResult(b)} = $res"
        Operation.SUBTRACTION    -> "${formatResult(a)} - ${formatResult(b)} = $res"
        Operation.MULTIPLICATION -> "${formatResult(a)} × ${formatResult(b)} = $res"
        Operation.DIVISION       -> "${formatResult(a)} ÷ ${formatResult(b)} = $res"
        Operation.MODULUS        -> "${formatResult(a)} % ${formatResult(b)} = $res"
        Operation.SQUARE_ROOT    -> "√${formatResult(a)} = $res"
        Operation.CUBE_ROOT      -> "∛${formatResult(a)} = $res"
        Operation.ROOT_OF_X      -> "${formatResult(a)}th root of ${formatResult(b)} = $res"
        Operation.EXPONENTIAL    -> "${formatResult(a)} ^ ${formatResult(b)} = $res"
        Operation.LOGARITHM      -> "log${formatResult(a)}(${formatResult(b)}) = $res"
    }
}


fun formatRawExpression(tokens: List<String>): String = tokens.joinToString(" ")


fun formatOrdinal(n: Double): String {
    val i = n.toLong()
    val suffix = when {
        i % 100 in 11L..13L -> "th"
        i % 10 == 1L         -> "st"
        i % 10 == 2L         -> "nd"
        i % 10 == 3L         -> "rd"
        else                  -> "th"
    }
    return "$i$suffix"
}
