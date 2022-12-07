package tw.gasol.aoc.aoc2022

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day2Test {

    private val input = readInput("/input/day2.txt")

    @Test
    fun part1() {
        val result = Day2().part1(input)
        assertEquals(10994, result)
    }

    @Test
    fun part2() {
        val result = Day2().part2(input)
        assertEquals(12526, result)
    }
}