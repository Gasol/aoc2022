package tw.gasol.aoc.aoc2022

class Day8 {
    fun part1(input: String): Int {
        val lines = input.lines().filterNot { it.isBlank() }
        val height = lines.size
        val width = lines.first().length

        val heights = lines.flatMap { it.toList() }
        val treeMap = TreeMap(width, height, heights)
//        treeMap.printVisibleMap()
        return treeMap.getVisibleCount()
    }

    fun part2(input: String) {

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