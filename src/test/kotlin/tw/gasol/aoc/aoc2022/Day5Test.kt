package tw.gasol.aoc.aoc2022

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day5Test {

    private val input = readInput("/input/day5.txt")

    @Test
    fun part1() {
        val result = Day5().part1(input)
        assertEquals("GFTNRBZPF", result)
    }

    @Test
    fun part2() {
        val result = Day5().part2(input)
        assertEquals("VRQWPDSGP", result)
    }
}