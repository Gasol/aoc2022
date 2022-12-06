package tw.gasol.aoc.aoc2022

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day2Test {

    private fun readInput(): String {
        return javaClass.getResourceAsStream("/input/day2.txt")
            ?.bufferedReader().use { it!!.readText() }
    }

    @Test
    fun part1() {
        val result = Day2().part1(readInput())
        assertEquals(10994, result)
    }

    @Test
    fun part2() {
    }
}