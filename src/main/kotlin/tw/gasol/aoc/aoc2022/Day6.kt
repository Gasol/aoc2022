package tw.gasol.aoc.aoc2022

class Day6 {

    private fun getDistinctSignalPosition(input: String, signalSize: Int = 4): IntRange? {
        for (i in 0..input.length) {
            val endPosition = i + signalSize
            if (endPosition > input.length) {
                break
            }
            val s = input.substring(i, endPosition)
            if (s.toSet().size == signalSize) {
                return i .. endPosition
            }
        }

        return null
    }

    fun part1(input: String): Int {
        return getDistinctSignalPosition(input, 4)?.endInclusive ?: 0
    }

    fun part2(input: String): Int {
        return getDistinctSignalPosition(input, 14)?.endInclusive ?: 0
    }
}