package tw.gasol.aoc.aoc2022

class Day6 {
    fun part1(input: String): Int {
        val markerSize = 4

        for (i in 0..input.length) {
            val endPosition = i + markerSize
            if (endPosition > input.length) {
                break
            }
            val s = input.substring(i, endPosition)
            if (s.toSet().size == markerSize) {
                return endPosition
            }
        }
        return 0
    }

    fun part2(input: String): Int {
        return 0
    }
}