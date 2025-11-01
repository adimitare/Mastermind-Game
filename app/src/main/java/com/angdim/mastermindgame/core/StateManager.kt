package com.angdim.mastermindgame.core

import kotlin.random.Random

enum class CharacterStatus { GREEN, ORANGE, RED }

object StateManager {
    private val alphabet: List<Char> = ('A'..'Z').toList()

    fun provideSecret(length: Int = 4, random: Random = Random): String {
        val sb = StringBuilder()
        repeat(length) {
            sb.append(alphabet[random.nextInt(alphabet.size)])
        }
        return sb.toString()
    }

    fun evaluateGuess(secret: String, guess: String): List<CharacterStatus> {
        require(secret.length == guess.length) { "Secret and guess must have the same length" }
        val n = secret.length
        val result = MutableList(n) { CharacterStatus.RED }

        val remaining = mutableMapOf<Char, Int>()

        for (i in 0 until n) {
            val s = secret[i]
            val g = guess[i]
            if (g == s) {
                result[i] = CharacterStatus.GREEN
            } else {
                remaining[s] = (remaining[s] ?: 0) + 1
            }
        }

        for (i in 0 until n) {
            if (result[i] == CharacterStatus.GREEN) continue
            val g = guess[i]
            val avail = remaining[g] ?: 0
            if (avail > 0) {
                result[i] = CharacterStatus.ORANGE
                remaining[g] = avail - 1
            }
        }

        return result
    }

    fun isWin(evaluation: List<CharacterStatus>): Boolean = evaluation.all { it == CharacterStatus.GREEN }
}