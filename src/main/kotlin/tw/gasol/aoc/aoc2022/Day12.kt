package tw.gasol.aoc.aoc2022

import java.util.*
import kotlin.Comparator
import kotlin.math.abs

enum class NodeType { Unspecific, Start, Goal }
data class Node(val x: Int, val y: Int, val z: Int, val type: NodeType) {
    fun distanceTo(other: Node): Int {
        return abs(x - other.x) + abs(y - other.y)
    }

    override fun toString(): String {
        val c = Char(z)
        val padStart = 0
        return buildString {
            append(c)
            append('[')
            append("$x".padStart(padStart, ' '))
            append(",")
            append("$y".padStart(padStart, ' '))
            append("|$z]")
        }
    }
}

class NodeComparator(private val start: Node, private val goal: Node, var currentHeight: Int? = null) :
    Comparator<Node> {

    private fun costTo(node: Node): Int {
        val heightCost = if (currentHeight != null) {
            abs(currentHeight!! - node.z + 1)
        } else 0
        return start.distanceTo(node) + goal.distanceTo(node) + heightCost
    }

    override fun compare(a: Node, b: Node): Int {
        return costTo(b) - costTo(a)
    }
}

class Day12 {
    fun part1(input: String): Int {
        val map = input.lines()
            .filterNot { it.isBlank() }
            .mapIndexed { rows, line ->
                line.toCharArray()
                    .mapIndexed { cols, c ->
                        val type = when (c) {
                            'S' -> NodeType.Start
                            'E' -> NodeType.Goal
                            else -> NodeType.Unspecific
                        }
                        val z = when (type) {
                            NodeType.Start -> 'a'.code
                            NodeType.Goal -> 'z'.code
                            else -> c.code
                        }
                        Node(cols, rows, z, type)
                    }
            }
        val rows = map.size
        val cols = map.first().size

        val start = map.map { line -> line.firstOrNull { it.type == NodeType.Start } }.firstNotNullOf { it }
        val end = map.map { line -> line.firstOrNull { it.type == NodeType.Goal } }.firstNotNullOf { it }
        val findNeighbors = { node: Node ->
            buildList {
                if (node.x > 0) add(map[node.y][node.x - 1])
                if (node.x < cols - 1) add(map[node.y][node.x + 1])
                if (node.y > 0) add(map[node.y - 1][node.x])
                if (node.y < rows - 1) add(map[node.y + 1][node.x])
            }.filter { it.z - node.z <= 1 }
        }

        val cost = NodeComparator(start, end)
        val path = aStartSearch(start, end, cost, findNeighbors) {
            cost.currentHeight = it.z
        }
        printMap(map, path)
        return path.size - 1
    }

    private fun <T> aStartSearch(
        start: T,
        goal: T,
        costComparator: Comparator<T>,
        neighbors: (T) -> List<T>,
        test: ((T) -> Unit)? = null
    ): List<T> {
        var frontier: PriorityQueue<T>? = PriorityQueue(costComparator)
        frontier!!.add(start)
        val cameFroms = mutableMapOf<T, T>()
        cameFroms[start] = start

        var nextFrontier: PriorityQueue<T>?
        while (frontier != null) {
            nextFrontier = PriorityQueue(costComparator)
            while (frontier.isNotEmpty()) {
                val current = frontier.poll()
                if (current == goal) {
                    break
                }
                test?.invoke(current)

                for (next in neighbors(current)) {
                    if (next !in cameFroms) {
                        nextFrontier.add(next)
                        cameFroms[next] = current
                    }
                }
            }
            if (nextFrontier.isEmpty()) {
                break
            }
            frontier = nextFrontier
        }
        val path = mutableListOf<T>()
        var current = goal
        while (current != start) {
            path.add(current)
            current = cameFroms[current]!!
        }
        path.add(start)
        path.reverse()
        return path
    }

    private fun printMap(map: List<List<Node>>, pathList: List<Node>? = null) {
        map.forEach { rows ->
            rows.forEach { node ->
                val index = pathList?.indexOf(node) ?: -1
                if (index >= 0) {
                    val nextIndex = index + 1
                    if (nextIndex < pathList!!.size) {
                        val nextNode = pathList[nextIndex]
                        val c = when (nextNode.x - node.x) {
                            1 -> '>'
                            -1 -> '<'
                            else -> when (nextNode.y - node.y) {
                                1 -> 'v'
                                -1 -> '^'
                                else -> ' '
                            }
                        }
                        print(c)
                    } else {
                        print('*')
                    }
                } else {
                    print(Char(node.z))
                }
            }
            println()
        }
    }
}