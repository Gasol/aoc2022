package tw.gasol.aoc.aoc2022

import org.jetbrains.annotations.TestOnly

class Day8 {
    fun part1(input: String): Int {
        val treeMap = buildTreeMap(input)
//        treeMap.printVisibleMap()
        return treeMap.getVisibleCount()
    }

    fun part2(input: String): Int {
        val treeMap = buildTreeMap(input)
        return treeMap.getHighestScenicScore()
    }

    fun buildTreeMap(input: String): TreeMap {
        val lines = input.lines().filterNot { it.isBlank() }
        val height = lines.size
        val width = lines.first().length

        val heights = lines.flatMap { it.toList() }
        return TreeMap(width, height, heights)
    }
}


class TreeMap(private val width: Int, private val height: Int, private val heights: List<Char>) {

    private val treeMap = CharArray(width * height) { index ->
        heights[index]
    }

    private val visibleMap = BooleanArray(width * height) { false }

    init {
        (0 until width).forEach { x ->
            var baseHeight: Char? = null
            getHeightList(x, 0, Direction.TOP).forEachIndexed { y, height ->
                val visible: Boolean
                if (baseHeight == null) {
                    baseHeight = height
                    visible = true
                } else if (height > baseHeight!!) {
                    visible = true
                    baseHeight = height
                } else visible = false
                setVisible(x, y, visible)
            }

            baseHeight = null
            getHeightList(x, 0, Direction.BOTTOM).forEachIndexed { reversedY, height ->
                val visible: Boolean
                if (baseHeight == null) {
                    baseHeight = height
                    visible = true
                } else if (height > baseHeight!!) {
                    visible = true
                    baseHeight = height
                } else visible = false
                val y = this.height - reversedY - 1
                val oldVisible = getVisible(x, y)
                setVisible(x, y, visible || oldVisible)
            }
        }

        (0 until height).forEach { y ->
            var baseHeight: Char? = null
            getHeightList(0, y, Direction.LEFT).forEachIndexed { x, height ->
                val visible: Boolean
                if (baseHeight == null) {
                    baseHeight = height
                    visible = true
                } else if (height > baseHeight!!) {
                    visible = true
                    baseHeight = height
                } else visible = false
                val oldVisible = getVisible(x, y)
                setVisible(x, y, visible || oldVisible)
            }

            baseHeight = null
            getHeightList(0, y, Direction.RIGHT).forEachIndexed { reversedX, height ->
                val visible: Boolean
                if (baseHeight == null) {
                    baseHeight = height
                    visible = true
                } else if (height > baseHeight!!) {
                    visible = true
                    baseHeight = height
                } else visible = false
                val x = this.height - reversedX - 1
                val oldVisible = getVisible(x, y)
                setVisible(x, y, visible || oldVisible)
            }
        }
    }

    fun get(x: Int, y: Int): Char {
        assert(x in 0 until width) { "x is out of range" }
        assert(y in 0 until height) { "y is out of range" }
        return treeMap[y * width + x]
    }

    private fun getHeightList(x: Int, y: Int, from: Direction): List<Char> {
        return when (from) {
            Direction.TOP -> (0 until height).map { get(x, it) }
            Direction.BOTTOM -> (height - 1 downTo 0).map { get(x, it) }
            Direction.LEFT -> (0 until width).map { get(it, y) }
            Direction.RIGHT -> (width - 1 downTo 0).map { get(it, y) }
        }
    }

    fun getVisibleCount() = visibleMap.count { it }

    fun printVisibleMap() {
        (0 until height).forEach { y ->
            (0 until width).forEach { x ->
                print(if (getVisible(x, y)) "1" else "0")
            }
            println()
        }
    }

    fun getHighestScenicScore(): Int {
        var highestScore = 0
        (0 until height).forEach { y ->
            (0 until width).forEach { x ->
                getScenicScore(x, y)
                    .takeIf { it > highestScore }
                    ?.let { highestScore = it }
            }
        }
        return highestScore
    }

    private fun computeScore(axis: Axis, pos: Int, range: IntProgression, treeHeight: Char): Int {
        var score = 0
        for (i in range) {
            val (x, y) = if (axis == Axis.X) {
                pos to i
            } else {
                i to pos
            }
            val height = get(x, y)
            score++
            if (height >= treeHeight) {
                break
            }
        }
        return score
    }

    @TestOnly

    fun computeUpScore(x: Int, y: Int, treeHeight: Char) =
        computeScore(Axis.X, x, y - 1 downTo 0, treeHeight)

    @TestOnly
    fun computeDownScore(x: Int, y: Int, treeHeight: Char) =
        computeScore(Axis.X, x, y + 1 until height, treeHeight)

    @TestOnly
    fun computeLeftScore(x: Int, y: Int, treeHeight: Char) =
        computeScore(Axis.Y, y, x - 1 downTo 0, treeHeight)

    @TestOnly
    fun computeRightScore(x: Int, y: Int, treeHeight: Char) =
        computeScore(Axis.Y, y, x + 1 until width, treeHeight)

    private fun getScenicScore(x: Int, y: Int): Int {
        val treeHeight = get(x, y)
        return arrayOf(::computeUpScore, ::computeLeftScore, ::computeRightScore, ::computeDownScore)
            .map { it(x, y, treeHeight) }
            .reduce(Math::multiplyExact)
    }

    private fun setVisible(x: Int, y: Int, value: Boolean) {
        assert(x in 0 until width) { "x ($x) is out of range" }
        assert(y in 0 until height) { "y ($y) is out of range" }
        visibleMap[y * width + x] = value
    }

    private fun getVisible(x: Int, y: Int): Boolean {
        assert(x in 0 until width) { "x ($x) is out of range" }
        assert(y in 0 until height) { "y ($y) is out of range" }
        return visibleMap[y * width + x]
    }
}

enum class Direction { TOP, BOTTOM, LEFT, RIGHT }
enum class Axis { X, Y }