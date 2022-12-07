package tw.gasol.aoc.aoc2022

class Day6 {

    private fun getDistinctSignalPosition(input: String, signalSize: Int = 4): Int =
        input.toList()
            .windowed(signalSize)
            .indexOfFirst { it.distinct().size == signalSize } + signalSize

    fun part1(input: String) = getDistinctSignalPosition(input, 4)

    fun part2(input: String) = getDistinctSignalPosition(input, 14)
}