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
        return input.lineSequence()
            .filterNot { it.isBlank() }
            .map(::toTwoSections)
            .filter { it.first.contains(it.second) || it.second.contains(it.first) }
            .count()
    }
}

fun IntRange.containsAll(other: IntRange): Boolean {
    return this.first <= other.first && this.last >= other.last
}

fun IntRange.contains(other: IntRange): Boolean {
    return (this.first <= other.first && this.last >= other.first) ||
            (this.first <= other.last && this.last >= other.last)
}

fun Pair<IntRange, IntRange>.isFullContained(): Boolean {
    val (first, second) = this

    return first.containsAll(second) || second.containsAll(first)
}