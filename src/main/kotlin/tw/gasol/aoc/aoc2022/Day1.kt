package tw.gasol.aoc.aoc2022

class Day1 {

    private fun getCaloriesGroups(input: String): Sequence<Sequence<Int>> {
        return input.splitToSequence("\n\n")
            .map { chunk ->
                chunk.lineSequence()
                    .filterNot { it.isBlank() }
                    .map { it.toInt() }
            }
    }

    private fun getCaloriesSums(input: String) =
        getCaloriesGroups(input).map { it.sum() }

    fun part1(input: String): Int {
        return getCaloriesSums(input)
            .sortedDescending()
            .first()
    }

    fun part2(input: String): Int {
        return getCaloriesSums(input)
            .sortedDescending()
            .take(3)
            .sum()
    }
}