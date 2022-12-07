package tw.gasol.aoc.aoc2022

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day7Test {

    private val input = readInput("/input/day7.txt")

    @Test
    fun part1() {
        val result = Day7().part1(input)
        assertEquals(1350966, result)
    }

    @Test
    fun part2() {
    }
}