package tw.gasol.aoc.aoc2022

import java.math.BigInteger
import java.util.*

typealias WorryLevel = BigInteger

data class MonkeySpec(
    val id: Int,
    val items: MutableList<WorryLevel>,
    val operation: (WorryLevel) -> WorryLevel,
    val testDivider: Int,
    val ifTrue: Int,
    val ifFalse: Int
) {

    override fun toString(): String {
        return "MonkeySpec(id=$id, items=$items)"
    }

    companion object {
        fun fromInput(input: String): MutableMap<Int, MonkeySpec> {
            val specs = mutableMapOf<Int, MonkeySpec>()
            val scanner = Scanner(input)
            while (scanner.hasNextLine()) {
                val spec = parseMonkeySpec(scanner)
                specs[spec.id] = spec
            }
            return specs
        }

        private fun parseMonkeySpec(scanner: Scanner): MonkeySpec {
            var id: Int? = null
            var items: List<WorryLevel>? = null
            var operation: ((WorryLevel) -> WorryLevel)? = null
            var testDivider: Int? = null
            var ifTrue: Int? = null
            var ifFalse: Int? = null

            while (scanner.hasNextLine()) {
                val line = scanner.nextLine().trim()
                when {
                    line.startsWith("Monkey") -> {
                        id = line.substringAfter("Monkey ").substringBefore(":").toInt()
                    }

                    line.startsWith("Starting items") -> {
                        items = line.substringAfter(": ").split(", ").map { it.toBigInteger() }
                    }

                    line.startsWith("Operation") -> {
                        val operationLine = line.substringAfter(":")
                        operation = { old ->
                            val (lval, op, rval) = operationLine.substringAfter("= ")
                                .trim()
                                .split(" ")
                            val lint = if (lval == "old") old else lval.toBigInteger()
                            val rint = if (rval == "old") old else rval.toBigInteger()
                            when (op) {
                                "+" -> lint + rint
                                "-" -> lint - rint
                                "*" -> lint * rint
                                "/" -> lint / rint
                                else -> throw IllegalArgumentException("Unknown operation: $op")
                            }
                        }
                    }

                    line.startsWith("Test") -> {
                        val testLine = line.substringAfter(": ")
                        val (_, value) = testLine.split(" by ")
                        testDivider = value.toInt()

                        repeat(2) {
                            val ifLine = scanner.nextLine().trim()
                            val (beforeLine, afterLine) = ifLine.split(": ")
                            when (beforeLine) {
                                "If true" -> {
                                    ifTrue = afterLine.substringAfter("monkey ").toInt()
                                }

                                "If false" -> {
                                    ifFalse = afterLine.substringAfter("monkey ").toInt()
                                }

                                else -> error("Unexpected line: $ifLine")
                            }
                        }
                    }

                    line.isBlank() -> break
                }
            }
            return MonkeySpec(id!!, items!!.toMutableList(), operation!!, testDivider!!, ifTrue!!, ifFalse!!)
        }
    }
}

class Day11 {
    fun part1(input: String): Int {
        val specs = MonkeySpec.fromInput(input)

        val monkeyIds = specs.keys.sorted()
        val monkeyInspectionCounts = mutableMapOf(*monkeyIds.map { it to 0 }.toTypedArray())

        val three = BigInteger.valueOf(3)
        for (round in 1..20) {
//            println("Round $round")
            monkeyIds.forEach { id ->
                val spec = specs[id]!!
                monkeyInspectionCounts[id] = monkeyInspectionCounts[id]!! + spec.items.count()
                with(spec.items.iterator()) {
                    forEach { item ->
                        val newItem = spec.operation(item) / three
                        val toMonkeyId =
                            if (newItem % spec.testDivider.toBigInteger() == BigInteger.ZERO) spec.ifTrue else spec.ifFalse
                        specs[toMonkeyId]!!.items += newItem
                        remove()
                    }
                }
            }
//            specs.forEach(::println)
        }
        return monkeyInspectionCounts.values
            .sorted()
            .takeLast(2)
//            .also { println(it) }
            .reduce { acc, i -> acc * i }
    }
}