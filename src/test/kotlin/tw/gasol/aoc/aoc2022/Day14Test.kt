package tw.gasol.aoc.aoc2022

import kotlin.test.Test
import kotlin.test.assertEquals

class Day14Test {
    private val sampleInput = """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent()

    private val input = readInput("/input/day14.txt")

    @Test
    fun part1Sample() {
        val result = Day14().part1(sampleInput)
        assertEquals(24, result)
    }

    @Test
    fun part2Sample() {
        val result = Day14().part2(sampleInput)
        assertEquals(93, result)
    }

    @Test
    fun part1() {
        val result = Day14().part1(input)
        assertEquals(964, result)
    }

    @Test
    fun part2() {
        val result = Day14().part2(input)
        assertEquals(32041, result)
    }
}