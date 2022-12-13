package tw.gasol.aoc.aoc2022

typealias Packet = List<Any>

class Day13 {
    fun part1(input: String): Int {
        val pairs = input.split("\n\n")
            .map {
                val (first, second) = it.split("\n")
                toPacket(first) to toPacket(second)
            }

        return pairs.map { isRightOrder(it.first, it.second) }
            .mapIndexed { index, b -> if (b) index + 1 else 0 }
            .sum()
    }

    fun isRightOrder(first: List<Any>, second: List<Any>): Boolean {
        for (i in first.indices) {
            val left = first.getOrNull(i)
            val right = second.getOrNull(i) ?: return false

            if (left is Int && right is Int) {
                if (left > right) {
                    return false
                }
            } else if (left is List<*> && right is List<*>) {
                if (!isRightOrder(left as List<Any>, right as List<Any>)) {
                    return false
                }
            } else if (left is Int && right is List<*>) {
                if (!isRightOrder(listOf(left), right as List<Any>)) {
                    return false
                }
            }
        }
        return true
    }

    fun toPacket(line: String): Packet {
        val lists = mutableListOf<MutableList<Any>>()
        val sb = StringBuilder()
        for (c in line.toCharArray()) {
            when (c) {
                '[' -> {
                    lists.add(mutableListOf())
                }

                ']' -> {
                    val item = sb.toString()
                    if (item.isNotBlank()) {
                        lists.last().add(item.toInt())
                        sb.clear()
                    }
                    val list = lists.removeLast()
                    if (lists.size == 0) {
                        return list
                    } else {
                        lists.last().add(list)
                    }
                }

                ',' -> {
                    val item = sb.toString()
                    if (item.isNotBlank()) {
                        lists.last().add(item.toInt())
                    }
                    sb.clear()
                }

                else -> sb.append(c)
            }
        }
        error("Invalid line - $line")
    }
}