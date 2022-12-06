package tw.gasol.aoc.aoc2022

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day1Test {
    @Test
    fun part1() {
        val inputText = javaClass.getResourceAsStream("/input/day1.txt").bufferedReader().use { it.readText() }
        val result = Day1().part1(inputText)
        assertEquals(69289, result)
    }
}