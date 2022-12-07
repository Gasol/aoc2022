package tw.gasol.aoc.aoc2022

import java.util.Scanner

class Day5 {

    fun part1(input: String): String {
        val stackBuilder = StringBuilder()
        var stacks: List<ArrayDeque<Char>>? = null
        input.lineSequence()
            .forEach { line ->
                if (stacks == null) {
                    if (line.isBlank()) {
                        stacks = buildStack(stackBuilder.toString())
                    } else {
                        stackBuilder.appendLine(line)
                    }
                } else if (line.isNotBlank()) {
                    val procedure = parseProcedure(line)
                    runProcedure(stacks!!, procedure)
                }
            }

        return stacks?.map { it.last() }?.joinToString("").orEmpty()
    }

    private fun runProcedure(stacks: List<ArrayDeque<Char>>, procedure: MoveProcedure) {
        repeat(procedure.quantity) {
            val from = stacks[procedure.from - 1]
            val to = stacks[procedure.to - 1]
            to.addLast(from.removeLast())
        }
    }

    private fun runProcedure9001(stacks: List<ArrayDeque<Char>>, procedure: MoveProcedure) { // CrateMover 9001
        (1..procedure.quantity).map { _ ->
            stacks[procedure.from - 1].removeLast()
        }.reversed().forEach { char ->
            stacks[procedure.to - 1].addLast(char)
        }
    }

    private fun parseProcedure(line: String) = MoveProcedure.fromString(line)

    private fun buildStack(input: String): List<ArrayDeque<Char>> {
        val crateLines = input.lines()
            .filterNot { it.isBlank() }
            .filterNot { it.startsWith(" 1 ") }

        val emptyCrateReplacement = "[-]"
        val crateLists = crateLines
            .map { line ->
                line.replace("^ {3}".toRegex(), emptyCrateReplacement)
                    .replace("    ", " $emptyCrateReplacement")
                    .split(" ")
                    .filterNot { it.isBlank() }
                    .map { it.trim()[1] }
            }
            .filterNot { it.isEmpty() }

        val stackSize = crateLists.maxOf { it.size }
        val stacks = buildList(stackSize) {
            repeat(stackSize) { add(ArrayDeque<Char>()) }
        }

        crateLists.flatten()
            .forEachIndexed { index, crate ->
                if (crate != '-') {
                    stacks[index % stacks.size].addFirst(crate)
                }
            }
        return stacks
    }

    fun part2(input: String): String {
        val stackBuilder = StringBuilder()
        var stacks: List<ArrayDeque<Char>>? = null
        input.lineSequence()
            .forEach { line ->
                if (stacks == null) {
                    if (line.isBlank()) {
                        stacks = buildStack(stackBuilder.toString())
                    } else {
                        stackBuilder.appendLine(line)
                    }
                } else if (line.isNotBlank()) {
                    val procedure = parseProcedure(line)
                    runProcedure9001(stacks!!, procedure)
                }
            }

        return stacks?.map { it.last() }?.joinToString("").orEmpty()
    }
}

data class MoveProcedure(val quantity: Int, val from: Int, val to: Int) {
    companion object {
        fun fromString(line: String): MoveProcedure = Scanner(line).use { scanner ->
            val command = scanner.next()
            assert(command == "move") { "Invalid command: $command" }
            val quantity = scanner.nextInt()
            assert(quantity >= 0) { "Invalid quantity: $quantity" }
            scanner.next()
            val from = scanner.nextInt()
            scanner.next()
            val to = scanner.nextInt()
            return MoveProcedure(quantity, from, to)
        }
    }
}