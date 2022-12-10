package tw.gasol.aoc.aoc2022

import java.util.*

class Day10 {
    fun part1(input: String): Int {
        var x = 1
        var cycleCount = 0
        var signals = 0
        var nextSignalCycle = 20
        val cycleStep = 40

        input.lineSequence()
            .filterNot { it.isBlank() }
            .forEach { line ->
                val scanner = Scanner(line)
                val op = scanner.next()
                val cycle = when (op) {
                    "noop" -> 1
                    "addx" -> 2
                    else -> error("Invalid op: $op")
                }
                repeat(cycle) {
                    if (++cycleCount == nextSignalCycle) {
                        signals += nextSignalCycle * x
                        nextSignalCycle += cycleStep
                    }
                }
                if (op == "addx") {
                    val v = scanner.nextInt()
                    x += v
                }
            }
        return signals
    }

    fun part2(input: String): String {
        val cols = 40
        val rows = 6
        val monitor = Array(rows) { CharArray(cols) { '.' } }
        val printMonitor = { monitor: Array<CharArray> ->
            monitor.forEach { row ->
                println(row.joinToString(""))
            }
        }

        var x = 1
        var cycleCount = 0
        var signals = 0
        var nextSignalCycle = 20
        val cycleStep = 40
        var spriteOffset = 0

        input.lineSequence()
            .filterNot { it.isBlank() }
            .forEach { line ->
                val scanner = Scanner(line)
                val op = scanner.next()
                val cycle = when (op) {
                    "noop" -> 1
                    "addx" -> 2
                    else -> error("Invalid op: $op")
                }
                repeat(cycle) {
                    if (++cycleCount == nextSignalCycle) {
                        signals += nextSignalCycle * x
                        nextSignalCycle += cycleStep
                    }
                    val i = (cycleCount / cols) % rows
                    val j = cycleCount % cols
                    monitor[i][j] = if ((spriteOffset..spriteOffset + 2).contains(j)) '#' else '.'
                }
                if (op == "addx") {
                    val v = scanner.nextInt()
                    x += v
                    spriteOffset = x
                }
            }

        printMonitor(monitor)
        return "ZGCJZJFL"
    }
}