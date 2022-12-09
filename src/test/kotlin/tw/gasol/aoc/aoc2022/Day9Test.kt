package tw.gasol.aoc.aoc2022

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Ignore

class Day9Test {

    private val input = readInput("/input/day9.txt")

    private val sampleInput = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent()

    @Test
    fun part1() {
        val result = Day9().part1(input)
        assertEquals(6057, result);
    }

    @Test
    @Ignore
    fun part2() {
    }

    @Test
    fun samplePart1() {
        val result = Day9().part1(sampleInput)
        assertEquals(13, result)
    }
}