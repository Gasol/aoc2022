package tw.gasol.aoc.aoc2022

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day4Test {

    @Test
    fun part1() {
        val result = Day4().part1(readInput())
        assertEquals(507, result)
    }

    private fun readInput(): String = javaClass.getResource("/input/day4.txt")
        ?.readText() ?: error("Input not found")

    @Test
    fun part2() {
    }
}