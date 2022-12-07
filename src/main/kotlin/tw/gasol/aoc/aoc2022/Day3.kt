package tw.gasol.aoc.aoc2022

class Day3 {
    fun part1(input: String): Int = input.lineSequence()
        .filterNot { it.isBlank() }
        .map(::findErrors)
        .map { getItemType(it.first()) }
        .sum()

    private fun findErrors(inputLine: String): Set<Char> {
        val length = inputLine.length
        val sepIndex = length / 2
        val set1 = inputLine.subSequence(0, sepIndex).toSet()
        val set2 = inputLine.subSequence(sepIndex, length).toSet()
        return set1.intersect(set2)
    }

    private fun getItemType(item: Char): Short {
        val baseCode = if (item.isUpperCase()) 'A'.code - 26 else 'a'.code
        val type = item.code - baseCode + 1
        return type.toShort()
    }

    fun part2(input: String): Int {
        return 0
    }
}