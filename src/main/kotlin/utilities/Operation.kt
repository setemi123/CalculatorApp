package utilities



enum class Operation(
    val command: String,
    val symbol: Char?,
    val label: String,
    val isBinary: Boolean
) {
    ADDITION      ("ADD",   '+',  "Addition",           true),
    SUBTRACTION   ("SUB",   '-',  "Subtraction",        true),
    MULTIPLICATION("MUL",   '*',  "Multiplication",     true),
    DIVISION      ("DIV",   '/',  "Division",           true),
    MODULUS       ("MOD",   '%',  "Modulus",            true),
    EXPONENTIAL   ("EXP",   '^',  "Exponential (a^b)",  true),
    SQUARE_ROOT   ("SQRT",  null, "Square Root",        false),
    CUBE_ROOT     ("CBRT",  null, "Cube Root",          false),
    ROOT_OF_X     ("ROOTX", null, "Root of X",          true),
    LOGARITHM     ("LOG",   null, "Logarithm (base a)", true);

    companion object {

        fun fromCommand(input: String): Operation? =
            entries.firstOrNull { it.command.equals(input.trim(), ignoreCase = true) }


        fun fromSymbol(symbol: Char): Operation? =
            entries.firstOrNull { it.symbol == symbol }
    }
}
