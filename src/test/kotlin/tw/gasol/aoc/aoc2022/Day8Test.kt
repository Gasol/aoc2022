package tw.gasol.aoc.aoc2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day8Test {

    private val input = readInput("/input/day8.txt")

    private val sampleInput = """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent()

    @Test
    fun part1() {
        val result = Day8().part1(input)
        assertEquals(1816, result)
    }

    @Test
    fun part2() {
        val result = Day8().part2(input)
        assertEquals(383520, result)
    }

    @Test
    fun samplePart1FirstExample() {
        val treeMap = Day8().buildTreeMap(sampleInput)
        val x = 2
        val y = 1
        val treeHeight = treeMap.get(x, y)
        val result = arrayOf(treeMap::computeUpScore, treeMap::computeLeftScore, treeMap::computeRightScore, treeMap::computeDownScore)
            .map { it(x, y, treeHeight) }
        assertEquals(listOf(1, 1, 2, 2), result)
    }

    @Test
    fun samplePart1SecondExample() {
        val treeMap = Day8().buildTreeMap(sampleInput)
        val x = 2
        val y = 3
        val treeHeight = treeMap.get(x, y)
        val result = arrayOf(treeMap::computeUpScore, treeMap::computeLeftScore, treeMap::computeRightScore, treeMap::computeDownScore)
            .map { it(x, y, treeHeight) }
        assertEquals(listOf(2, 2, 2, 1), result)
    }
}