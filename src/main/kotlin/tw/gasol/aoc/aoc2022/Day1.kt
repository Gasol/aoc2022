package tw.gasol.aoc.aoc2022

class Day1 {
    fun part1(input: String): Int {
        val caloriesGroups = input.splitToSequence("\n\n")
            .map { chunk ->
                chunk.splitToSequence("\n")
                    .filterNot { it.isBlank() }
                    .map { it.toInt() }
                    .sum()
            }
        return caloriesGroups
            .sortedDescending()
            .first()
    }

    fun part2(input: String): Int {
        return 0
    }
}