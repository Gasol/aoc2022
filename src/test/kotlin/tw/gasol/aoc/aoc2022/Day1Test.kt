package tw.gasol.aoc.aoc2022

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day1Test {
    fun readInput(): String {
        return javaClass.getResourceAsStream("/input/day1.txt")
            ?.bufferedReader().use { it!!.readText() }
    }

    @Test
    fun part1() {
        val result = Day1().part1(readInput())
        assertEquals(69289, result)
    }

    @Test
    fun part2() {
        val result = Day1().part2(readInput())
        assertEquals(205615, result)
    }
}