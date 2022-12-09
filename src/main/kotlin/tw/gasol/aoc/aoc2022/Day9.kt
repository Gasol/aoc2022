package tw.gasol.aoc.aoc2022

import kotlin.math.absoluteValue

typealias Position = Pair<Int, Int>

class Day9 {
    enum class Direction {
        UP, DOWN, LEFT, RIGHT;

        companion object {
            fun fromString(str: String): Direction {
                return when (str) {
                    "U" -> UP
                    "D" -> DOWN
                    "L" -> LEFT
                    "R" -> RIGHT
                    else -> throw IllegalArgumentException("Unknown direction: $str")
                }
            }
        }
    }

    fun part1(input: String): Int {

        val locations = mutableListOf<Position>()
        locations.add(0 to 0) // s
        var previousDirection: Direction? = null
        readMovements(input).forEach { movement ->
            val (direction, distance) = movement
            val last = locations.last()
            val (lastX, lastY) = last
            val (offsetX, offsetY) = when (direction) {
                Direction.UP -> 0 to 1
                Direction.DOWN -> 0 to -1
                Direction.LEFT -> -1 to 0
                Direction.RIGHT -> 1 to 0
            }
            val movedLocations =
                (1..distance).map {
                    Position(lastX + (offsetX * it), lastY + (offsetY * it))
                }
            if (previousDirection != null && previousDirection != direction) {
                locations.removeLast()
            }
            locations.addAll(movedLocations)
            previousDirection = direction
        }

        return locations.toSet().size - 1
    }

    private fun readMovements(input: String): List<Pair<Direction, Int>> {
        val regex = "(\\w) (\\d+)".toRegex()
        return input.lines()
            .filterNot { it.isBlank() }
            .map { line ->
                val (direction, distance) = regex.matchEntire(line)
                    ?.destructured ?: error("Invalid input line: $line")
                Pair(Direction.fromString(direction), distance.toInt())
            }
    }

    fun part2(input: String): Int {
        val movements = readMovements(input)
        val rope = Rope(10)
        return rope.countTailVisited(movements)
    }

    class Rope(private val numKnots: Int) {
        fun countTailVisited(movements: List<Pair<Direction, Int>>): Int {
            val visited = mutableSetOf<Position>()
            for (movement in movements) {
                move(movement) {
                    val tail = knots.last()
                    visited.add(Position(tail.x, tail.y))
                }
            }
            return visited.count()
        }

        private fun move(movement: Pair<Direction, Int>, onMoved: () -> Unit = {}) {
            val (direction, distance) = movement
            repeat(distance) {
                knots[0].move(direction)
                for (i in 1 until knots.size) {
                    knots[i].follow(knots[i - 1])
                }
                onMoved()
            }
        }

        data class Knot(var initX: Int, var initY: Int) {
            var x = initX
                private set
            var y = initY
                private set

            fun move(direction: Direction) {
                val (offsetX, offsetY) = when (direction) {
                    Direction.UP -> 0 to 1
                    Direction.DOWN -> 0 to -1
                    Direction.LEFT -> -1 to 0
                    Direction.RIGHT -> 1 to 0
                }

                x += offsetX
                y += offsetY
            }

            fun follow(front: Knot) {
                val diffX = front.x - x
                val diffY = front.y - y
                if (diffX.absoluteValue < 2 && diffY.absoluteValue < 2) {
                    return
                }

                if (diffX == 0) {
                    y += diffY / 2
                } else if (diffX == 1) {
                    x += 1
                    y += diffY / 2
                } else if (diffX == -1) {
                    x -= 1
                    y += diffY / 2
                } else if (diffY == 1) {
                    x += diffX / 2
                    y += 1
                } else if (diffY == 0) {
                    x += diffX / 2
                } else if (diffY == -1) {
                    x += diffX / 2
                    y -= 1
                } else {
                    x += diffX / 2
                    y += diffY / 2
                }
            }

            override fun toString(): String {
                return "Knot($x, $y)"
            }
        }

        private val knots = buildList {
            repeat(numKnots) { add(Knot(0, 0)) }
        }
    }
}