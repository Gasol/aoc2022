package tw.gasol.aoc.aoc2022

import java.awt.Point
import java.awt.Rectangle

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
        val beaconsPresent = testCoverage(pairPointsList, row)
        val points = pairPointsList.flatMap { it.toList() }
        return beaconsPresent.filterNot { points.contains(it.key) }.onEach {
            println("${it.key} ${it.value}")
        }.count { it.value }
    }

    private fun testCoverage(pairPointsList: List<Pair<Point, Point>>, row: Int): MutableMap<Point, Boolean> {
        val points = pairPointsList.flatMap { it.toList() }
        val minX = points.minOf { it.x }
        val maxX = points.maxOf { it.x }

        val beaconsPresent = mutableMapOf<Point, Boolean>()
        val rectangles = pairPointsList.map { (sensor, beacon) ->
            val size = sensor.distance(beacon).toInt()
            Rectangle(sensor.x - size, sensor.y, 0, 0).also {
                it.add(sensor.x + size, sensor.y)
                it.add(sensor.x, sensor.y + size)
                it.add(sensor.x, sensor.y - size)
            }
        }
        for (i in minX..maxX) {
            val point = Point(i, row)
            beaconsPresent[point] = rectangles.any { it.contains(point) }
        }
        return beaconsPresent
    }
}