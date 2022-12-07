package tw.gasol.aoc.aoc2022

fun readInput(path: String): String {
    return Unit.javaClass
        .getResource(path)
        ?.readText() ?: error("Input not found")
}