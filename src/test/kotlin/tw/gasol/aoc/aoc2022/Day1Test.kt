package tw.gasol.aoc.aoc2022

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day1Test {
    private val input = readInput("/input/day1.txt")


    @Test
    fun part1() {
        val result = Day1().part1(input)
        assertEquals(69289, result)
    }

    @Test
    fun part2() {
        val result = Day1().part2(input)
        assertEquals(205615, result)
    }
}