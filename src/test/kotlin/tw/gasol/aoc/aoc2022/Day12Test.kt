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

    @Test
    fun testPart1Sample() {
        assertEquals(31, Day12().part1(sampleInput))
    }
}