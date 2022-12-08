package tw.gasol.aoc.aoc2022

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day8Test {

    private val input = readInput("/input/day8.txt")

    @Test
    fun part1() {
        val result = Day8().part1(input)
        assertEquals(1816, result)
    }

    @Test
    fun part2() {
    }
}