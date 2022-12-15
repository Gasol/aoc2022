package tw.gasol.aoc.aoc2022

import java.awt.Point
import java.awt.geom.Path2D
import kotlin.math.abs

class Day15 {
    private fun getPairPointList(input: String): List<Pair<Point, Point>> {
        val numbersPattern = "[\\d\\-]+"
        val regex =
            Regex("Sensor at x=($numbersPattern), y=($numbersPattern): closest beacon is at x=($numbersPattern), y=($numbersPattern)")
        return input.lines()
            .filterNot { it.isBlank() }
            .map {
                try {
                    val (x1, y1, x2, y2) = regex.matchEntire(it)!!.destructured
                    Point(x1.toInt(), y1.toInt()) to Point(x2.toInt(), y2.toInt())
                } catch (e: Exception) {
                    error("Failed to parse $it")
                }
            }
    }

    fun part1(input: String, row: Int): Int {
        val pairPointsList = getPairPointList(input)
        return testCoverage(pairPointsList, row)
    }

    private fun testCoverage(pairPointsList: List<Pair<Point, Point>>, row: Int): Int {
        val points = pairPointsList.flatMap { it.toList() }
        var minX = points.minOf { it.x }
        var maxX = points.maxOf { it.x }

        val rectangles = pairPointsList.map { (sensor, beacon) ->
            val size = sensor.distanceTo(beacon)
            minX = minOf(minX, sensor.x - size)
            maxX = maxOf(maxX, sensor.x + size)
            Rectangle(
                Point(sensor.x - size, sensor.y),
                Point(sensor.x, sensor.y - size),
                Point(sensor.x, sensor.y + size + 1),
                Point(sensor.x + size + 1, sensor.y)
            )
        }
        var count = 0
        for (i in minX..maxX) {
            val point = Point(i, row)
            if (!points.contains(point) && rectangles.any { it.contains(point) }) {
                count++
            }
        }
        return count
    }
}

data class Rectangle(val topLeft: Point, val topRight: Point, val bottomLeft: Point, val bottomRight: Point) {

    private val path = Path2D.Double().also {
        it.moveTo(topLeft.x.toDouble(), topLeft.y.toDouble())
        it.lineTo(topRight.x.toDouble(), topRight.y.toDouble())
        it.lineTo(bottomRight.x.toDouble(), bottomRight.y.toDouble())
        it.lineTo(bottomLeft.x.toDouble(), bottomLeft.y.toDouble())
        it.closePath()
    }

    fun contains(point: Point) = path.contains(point)
}

fun Point.distanceTo(other: Point): Int {
    return abs(this.x - other.x) + abs(this.y - other.y)
}
