package tw.gasol.aoc.aoc2022.day2

data class StrategyGuide(val opponent: Guess, val me: Guess) {
    fun getScore(): Int {
        val bonusScore = runCatching {
            if (me.defeat(opponent)) 6 else 0
        }.getOrDefault(3)

        return me.score + bonusScore
    }
}