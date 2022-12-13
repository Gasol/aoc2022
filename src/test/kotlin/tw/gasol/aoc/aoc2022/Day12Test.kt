package tw.gasol.aoc.aoc2022

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day12Test {
    private val sampleInput = """
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi
    """.trimIndent()

    private val input = readInput("/input/day12.txt")

    @Test
    fun testPart1Sample() {
        assertEquals(31, Day12().part1(sampleInput))
    }

    @Test
    fun testPart1() {
        assertEquals(520, Day12().part1(input))
    }
}