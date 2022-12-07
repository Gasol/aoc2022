package tw.gasol.aoc.aoc2022

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day3Test {

    private fun readInput(): String =
        javaClass.getResourceAsStream("/input/day3.txt")
            ?.bufferedReader().use { return it!!.readText() }

    @Test
    fun part1() {
        val result = Day3().part1(readInput())
        assertEquals(7889, result)
    }

    @Test
    fun part2() {
    }
}