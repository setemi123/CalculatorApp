
package org.example


fun showHowTo() {
    val help = runCatching {
        object {}.javaClass.getResourceAsStream("/how-to.md")
            ?.bufferedReader()
            ?.readText()
    }.getOrNull()

    println(help ?: "Terminal Calculator\n\nPress ENTER to continue...")
    print("\nPress ENTER to continue...")
    readlnOrNull()
}
