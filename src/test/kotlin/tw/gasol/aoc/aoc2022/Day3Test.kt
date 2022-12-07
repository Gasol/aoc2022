package tw.gasol.aoc.aoc2022

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day3Test {

    private val input = readInput("/input/day3.txt")

    @Test
    fun part1() {
        val result = Day3().part1(input)
        assertEquals(7889, result)
    }

    @Test
    fun part2() {
        val result = Day3().part2(input)
        assertEquals(2825, result)
    }
}