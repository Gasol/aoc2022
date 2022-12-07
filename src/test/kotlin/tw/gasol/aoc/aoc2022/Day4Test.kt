package tw.gasol.aoc.aoc2022

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day4Test {

    private val input = readInput("/input/day4.txt")

    @Test
    fun part1() {
        val result = Day4().part1(input)
        assertEquals(507, result)
    }

    @Test
    fun part2() {
        val result = Day4().part2(input)
        assertEquals(897, result)
    }
}