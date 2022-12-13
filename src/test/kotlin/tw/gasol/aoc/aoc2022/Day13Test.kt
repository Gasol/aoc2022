package tw.gasol.aoc.aoc2022

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day13Test {
    private val input = readInput("/input/day13.txt")

    private val sampleInput = """
        [1,1,3,1,1]
        [1,1,5,1,1]

        [[1],[2,3,4]]
        [[1],4]

        [9]
        [[8,7,6]]

        [[4,4],4,4]
        [[4,4],4,4,4]

        [7,7,7,7]
        [7,7,7]

        []
        [3]

        [[[]]]
        [[]]

        [1,[2,[3,[4,[5,6,7]]]],8,9]
        [1,[2,[3,[4,[5,6,0]]]],8,9]
    """.trimIndent()

    @Test
    fun sampleInputPart1() {
        val result = Day13().part1(sampleInput)
        assertEquals(13, result)
    }

    @Test
    fun sampleInputPart2() {
        val result = Day13().part2(sampleInput)
        assertEquals(140, result)
    }

    @Test
    fun sortPackets() {
        val dividerPackets = listOf("[[2]]", "[[6]]")
        val day13 = Day13()
        val packets = buildList {
            addAll(dividerPackets.map { day13.toPacket(it) })
            addAll(
                sampleInput.lines()
                    .filterNot { it.isBlank() }
                    .map { day13.toPacket(it) }
            )
        }
        val sortedPackets = Day13().sortPackets(packets)

        assertEquals(
            """
            []
            [[]]
            [[[]]]
            [1,1,3,1,1]
            [1,1,5,1,1]
            [[1],[2,3,4]]
            [1,[2,[3,[4,[5,6,0]]]],8,9]
            [1,[2,[3,[4,[5,6,7]]]],8,9]
            [[1],4]
            [[2]]
            [3]
            [[4,4],4,4]
            [[4,4],4,4,4]
            [[6]]
            [7,7,7]
            [7,7,7,7]
            [[8,7,6]]
            [9]
        """.trimIndent(),
            sortedPackets.joinToString("\n")
                .replace(" ", "")
        )
    }

    @Test
    fun part1() {
        val result = Day13().part1(input)
        assertEquals(5580, result)
    }

    @Test
    fun testRightOrder() {
        val pairs = listOf(
            "[]" to "[3]",
            "[[4,4],4,4]" to "[[4,4],4,4,4]",
            "[1,1,3,1,1]" to "[1,1,5,1,1]",
            "[[1],[2,3,4]]" to "[[1],4]",
        )

        val day13 = Day13()
        for ((first, second) in pairs) {
            assertTrue(
                day13.isRightOrder(
                    day13.toPacket(first),
                    day13.toPacket(second)
                )!!,
                "$first should be right order than $second"
            )
        }
    }

    @Test
    fun testNotRightOrder() {
        val pairs = listOf(
            "[9]" to "[[8,7,6]]",
            "[7,7,7,7]" to "[7,7,7]",
            "[[[]]]" to "[[]]",
            "[1,[2,[3,[4,[5,6,7]]]],8,9]" to "[1,[2,[3,[4,[5,6,0]]]],8,9]",
        )

        val day13 = Day13()
        for ((first, second) in pairs) {
            assertFalse(
                day13.isRightOrder(
                    day13.toPacket(first),
                    day13.toPacket(second)
                )!!,
                "$first should not be right order than $second"
            )
        }
    }
}