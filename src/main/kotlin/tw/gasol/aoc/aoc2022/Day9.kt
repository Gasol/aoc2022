package tw.gasol.aoc.aoc2022

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
        val regex = "(\\w) (\\d+)".toRegex()
        val movements = input.lines()
            .filterNot { it.isBlank() }
            .map { line ->
                val (direction, distance) = regex.matchEntire(line)
                    ?.destructured ?: error("Invalid input line: $line")
                Pair(Direction.fromString(direction), distance.toInt())
            }
        val locations = mutableListOf<Position>()
        locations.add(0 to 0) // s
        var previousDirection: Direction? = null
        movements.forEach { movement ->
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

    fun part2(input: String): Int {
        return 0
    }
}