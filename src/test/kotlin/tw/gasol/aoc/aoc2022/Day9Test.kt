package tw.gasol.aoc.aoc2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day9Test {

    private val input = readInput("/input/day9.txt")

    private val sampleInput = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent()

    @Test
    fun part1() {
        val result = Day9().part1(input)
        assertEquals(6057, result);
    }

    @Test
    fun part2() {
        val result = Day9().part2(input)
        assertEquals(2514, result);
    }

    @Test
    fun samplePart1() {
        val result = Day9().part1(sampleInput)
        assertEquals(13, result)
    }

    @Test
    fun samplePart2() {
        val result = Day9().part2(sampleInput)
        assertEquals(1, result)
    }

    @Test
    fun testFollowWithNoOp() {
        val head = Day9.Rope.Knot(0, 0)
        for (x in -1..1) {
            for (y in -1..1) {
                val tail = Day9.Rope.Knot(x, y)
                tail.follow(head)
                assertEquals(x, tail.x)
                assertEquals(y, tail.y)
            }
        }
    }

    @Test
    fun testFollowByMoveOneStepDiagonally() {
        val head = Day9.Rope.Knot(0, 0)

        val test = listOf(
            Pair(-1, -2) to Pair(0, -1),
            Pair(1, -2) to Pair(0, -1),
            Pair(2, -2) to Pair(1, -1),
            Pair(2, -1) to Pair(1, 0),
            Pair(2, 0) to Pair(1, 0),
            Pair(2, 1) to Pair(1, 0),
            Pair(2, 2) to Pair(1, 1),
            Pair(0, 2) to Pair(0, 1),
            Pair(0, -2) to Pair(0, -1),
        )
        for ((input, expected) in test) {
            val (x, y) = input
            val tail = Day9.Rope.Knot(x, y)
            tail.follow(head)
            assertEquals(expected, tail.x to tail.y)
        }
    }
}