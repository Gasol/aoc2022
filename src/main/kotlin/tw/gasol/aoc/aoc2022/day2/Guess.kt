package tw.gasol.aoc.aoc2022.day2

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