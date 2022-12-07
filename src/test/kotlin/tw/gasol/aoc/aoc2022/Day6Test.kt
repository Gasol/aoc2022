package tw.gasol.aoc.aoc2022

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day6Test {

    private val input = readInput("/input/day6.txt")

    @Test
    fun part1() {
        val result = Day6().part1(input)
        assertEquals(1794, result)
    }

    @Test
    fun part2() {
    }
}