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
            .mapIndexed { index, b -> if (b!!) index + 1 else 0 }
            .sum()
    }

    fun isRightOrder(first: List<Any>, second: List<Any>): Boolean? {
        for (i in first.indices) {
            val left = first.getOrNull(i)
            val right = second.getOrNull(i)

            if (left is Int && right is Int) {
                return if (left > right) {
                    false
                } else if (left < right) {
                    true
                } else {
                    continue
                }
            } else if (left is List<*> && right is List<*>) {
                isRightOrder(left as List<Any>, right as List<Any>)?.let { return it }
            } else if (left is Int && right is List<*>) {
                isRightOrder(listOf(left), right as List<Any>)?.let { return it }
            } else if (right is Int && left is List<*>) {
                isRightOrder(left as List<Any>, listOf(right))?.let { return it }
            }
        }
        return if (first.size > second.size) {
            false
        } else if (first.size < second.size) {
            true
        } else {
            null
        }
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

    fun sortPackets(packets: List<Packet>): List<Packet> {
        return packets.sortedWith { o1, o2 ->
            isRightOrder(o1, o2)?.let {
                if (it) {
                    -1
                } else {
                    1
                }
            } ?: 0
        }
    }
}