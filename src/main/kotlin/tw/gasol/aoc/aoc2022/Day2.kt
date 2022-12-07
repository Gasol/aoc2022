package tw.gasol.aoc.aoc2022

import tw.gasol.aoc.aoc2022.day2.Guess
import tw.gasol.aoc.aoc2022.day2.StrategyGuide

class Day2 {
    fun part1(input: String): Int {
        val result = input.lineSequence()
            .filterNot { it.isBlank() }
            .map { line ->
                val splits = line.split(" ")
                assert(splits.size == 2) { "Invalid line: $line" }
                val opponent = Guess.fromString(splits[0])
                val me = Guess.fromString(splits[1])
                StrategyGuide(opponent, me).getScore()
            }
        return result.sum()
    }

    fun part2(input: String): Int {
        val result = input.lineSequence()
            .filterNot { it.isBlank() }
            .map { line ->
                val splits = line.split(" ")
                assert(splits.size == 2) { "Invalid line: $line" }
                val (opponentGuess, strategy) = splits
                val opponent = Guess.fromString(opponentGuess)
                val me = opponent.offer(strategy)
                StrategyGuide(opponent, me).getScore()
            }
        return result.sum()
    }
}