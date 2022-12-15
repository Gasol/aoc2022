package tw.gasol.aoc.aoc2022

import java.util.*

class Day14 {

    private val print: Boolean = false

    private fun parseRockCoordinates(input: String): List<List<Location>> {
        return input.lines()
            .filterNot { it.isBlank() }
            .map { line ->
                line.split(" -> ").map {
                    try {
                        it.split(",").let { (first, second) ->
                            Location(first.toInt(), second.toInt())
                        }
                    } catch (exception: IndexOutOfBoundsException) {
                        error("Invalid line \"$it\"")
                    }
                }
            }
    }

    fun part1(input: String): Int {
        val structure = Structure(parseRockCoordinates(input))
        val sands = structure.pourSands(Location(500, 0))
        if (print) {
            structure.print(true, sands)
        }
        return sands.size
    }

    fun part2(input: String): Int {
        val coordinates = parseRockCoordinates(input)
        val minPosition =
            coordinates.map { it.sortedBy { it.position }.first() }.sortedBy { it.position }.minOf { it.position }
        val maxPosition =
            coordinates.map { it.sortedBy { it.position }.last() }.sortedBy { it.position }.maxOf { it.position }
        val maxHeight = coordinates.map { it.sortedBy { it.height }.last() }.maxOf { it.height }
        val floorDepth = 2
        val floorCoordinates = coordinates.toMutableList().also {
            it.add(
                listOf(
                    Location(minPosition - maxHeight - floorDepth, maxHeight + floorDepth),
                    Location(maxPosition + maxHeight + floorDepth, maxHeight + floorDepth)
                )
            )
        }
        val structure = Structure(floorCoordinates)
        val sands = structure.pourSands(Location(500, 0))
        if (print) {
            structure.print(true, sands)
        }
        return sands.size
    }
}

enum class Symbol(private val char: Char) {
    Air('.'), Rock('#'), Sand('o'), Plus('+');

    override fun toString(): String {
        return char.toString()
    }
}

infix fun Int.toward(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to, step)
}

data class Location(val position: Int, val height: Int) {
    fun moveDown(): Location {
        return Location(position, height + 1)
    }

    fun moveLeft(): Location {
        return Location(position - 1, height)
    }

    fun moveRight(): Location {
        return Location(position + 1, height)
    }

    fun moveUp(): Location {
        return Location(position, height - 1)
    }
}

class Structure(coordinates: List<List<Location>>) {
    private val map = buildMap(coordinates)
    private fun buildMap(coordinatesList: List<List<Location>>): Map<Int, List<Symbol>> {
        val map = mutableMapOf<Int, List<Symbol>>()

        val setRock = { pos: Int, height: Int ->
            val oldList = map.getOrDefault(pos, emptyList())
            val capacity = height + 1
            val list = if (oldList.size < capacity) {
                buildList(capacity) {
                    addAll(oldList)
                    repeat(capacity - size) {
                        add(Symbol.Air)
                    }
                }
            } else {
                oldList
            }
            val changedList = list.toMutableList().also {
                it[height] = Symbol.Rock
            }

            map[pos] = changedList
        }
        for (coordinateLine in coordinatesList) {
            for ((from, to) in coordinateLine.windowed(2)) {
                val isHorizontalLine = from.position != to.position
                if (isHorizontalLine) {
                    for (position in from.position toward to.position) {
                        setRock(position, from.height)
                    }
                } else {
                    for (height in from.height toward to.height) {
                        setRock(from.position, height)
                    }
                }
            }
        }
        return map.toSortedMap(reverseOrder())
    }

    fun print(reverseOrder: Boolean = true, sands: Set<Location>? = null) {
        val height = map.maxOf { it.value.size }
        val headerHeight = map.maxOf { it.key.toString().length }
        val paddingStart = height.toString().length

        val keys = if (reverseOrder) map.keys.reversed() else map.keys
        for (i in 0 until headerHeight) {
            print(" ".repeat(paddingStart + 1))
            for (j in keys) {
                val k = j.toString()
                print(k.getOrElse(i) { ' ' })
            }
            println()
        }

        for (i in 0 toward height) {
            print(i.toString().padStart(paddingStart) + " ")
            for (j in keys) {
                val list = map[j]!!
                if (j == 500 && i == 0) {
                    print(Symbol.Plus)
                } else {
                    val containsSand = sands?.contains(Location(j, i)) ?: false
                    if (containsSand) {
                        print(Symbol.Sand)
                    } else {
                        print(list.getOrNull(i) ?: Symbol.Air)
                    }
                }
            }
            println()
        }
    }

    fun pourSands(start: Location): Set<Location> {
        val sandsMap = mutableMapOf<Location, Boolean>()
        while (true) {
            addSand(sandsMap, start) ?: break
//            print(true, sandsMap.keys.toSet())
        }
        return sandsMap.keys.toSet()
    }

    private fun addSand(sandsMap: MutableMap<Location, Boolean>, start: Location): Location? {
        val queue = PriorityQueue<Location>(compareBy { it.position })
        queue.add(start)

        var getSandOrSymbol = { location: Location ->
            if (sandsMap.keys.contains(location)) Symbol.Sand else getSymbol(location)
        }
        while (queue.isNotEmpty()) {
            val current = queue.poll()
            val list = map[current.position] ?: return null
            if (list.size < current.height) {
                return null
            }
            val symbol = getSandOrSymbol(current) ?: return null
//            println("${current.position},${current.height.toString().padStart(3)}: $symbol")
            when (symbol) {
                Symbol.Sand, Symbol.Rock -> {
                    val left = current.moveLeft()
                    val right = current.moveRight()
                    val leftSymbol = getSandOrSymbol(left) ?: return null
                    val rightSymbol = getSandOrSymbol(right) ?: return null
//                    println("$leftSymbol$symbol$rightSymbol")
                    if ((leftSymbol == Symbol.Sand || leftSymbol == Symbol.Rock)
                        && (rightSymbol == Symbol.Sand || rightSymbol == Symbol.Rock)
                    ) {
                        val sand = current.moveUp()
                        sandsMap[sand] = true
                        if (sand == start) {
                            return null
                        }
                        return sand
                    }
                    if (leftSymbol == Symbol.Air) {
                        queue.add(left)
                    } else {
                        queue.add(right)
                    }
                }

                Symbol.Air, Symbol.Plus -> {
                    queue.add(current.moveDown())
                }
            }
        }

        return null
    }

    private fun getSymbol(location: Location) = map[location.position]?.getOrNull(location.height)
}