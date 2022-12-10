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
}