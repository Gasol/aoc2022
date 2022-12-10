package tw.gasol.aoc.aoc2022

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day10Test {
    private val input = readInput("/input/day10.txt")

    private val sampleInput = readInput("/input/day10_sample.txt")

    @Test
    fun testPart1Sample() {
        assertEquals(13140, Day10().part1(sampleInput))
    }

    @Test
    fun testPart1() {
        assertEquals(14040, Day10().part1(input))
    }

    @Test
    fun part2() {
        assertEquals("ZGCJZJFL", Day10().part2(input))
    }
}