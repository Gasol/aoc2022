package tw.gasol.aoc.aoc2022

class Day4 {
    fun part1(input: String): Int {
        return input.lineSequence()
            .filterNot { it.isBlank() }
            .map(::toTwoSections)
            .filter { it.isFullContained() }
            .count()
    }

    private fun toTwoSections(line: String): Pair<IntRange, IntRange> {
        val (first, second) = line.split(",")
            .map {
                it.split("-")
                    .map { num -> num.toInt() }
                    .let { (start, end) -> start..end }
            }
        return first to second
    }

    fun part2(input: String): Int {
        return 0
    }
}

fun Pair<IntRange, IntRange>.isFullContained(): Boolean {
    val (first, second) = this
    return (first.first <= second.first && first.last >= second.last) ||
            (second.first <= first.first && second.last >= first.last)
}