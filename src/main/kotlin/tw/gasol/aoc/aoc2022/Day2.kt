package tw.gasol.aoc.aoc2022

enum class Guess(val score: Int) {
    Rock(1), Paper(2), Scissor(3);

    fun defeat(other: Guess): Boolean {
        if (this == other) throw IllegalStateException("Draw")
        return when (this) {
            Rock -> other == Scissor
            Paper -> other == Rock
            Scissor -> other == Paper
        }
    }

    fun offer(strategy: String): Guess =
        when (strategy) {
            "X" -> { // lose
                when (this) {
                    Rock -> Scissor
                    Paper -> Rock
                    Scissor -> Paper
                }
            }

            "Y" -> { // draw
                when (this) {
                    Rock -> Rock
                    Paper -> Paper
                    Scissor -> Scissor
                }
            }

            "Z" -> { // win
                when (this) {
                    Rock -> Paper
                    Paper -> Scissor
                    Scissor -> Rock
                }
            }

            else -> {
                throw IllegalArgumentException("Invalid strategy: $strategy")
            }
        }

    companion object {
        fun fromString(str: String): Guess {
            return when (str) {
                "A", "X" -> Rock
                "B", "Y" -> Paper
                "C", "Z" -> Scissor
                else -> throw IllegalArgumentException("Invalid guess: $str")
            }
        }
    }
}

data class StrategyGuide(val opponent: Guess, val me: Guess) {
    fun getScore(): Int {
        val bonusScore = runCatching {
            if (me.defeat(opponent)) 6 else 0
        }.getOrDefault(3)

        return me.score + bonusScore
    }
}

class Day2 {
    fun part1(input: String): Int {
        val result = input.splitToSequence("\n")
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
        val result = input.splitToSequence("\n")
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