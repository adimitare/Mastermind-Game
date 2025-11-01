package com.angdim.mastermindgame.ui

import com.angdim.mastermindgame.core.CharacterStatus.GREEN
import com.angdim.mastermindgame.core.CharacterStatus.ORANGE
import com.angdim.mastermindgame.core.CharacterStatus.RED
import com.angdim.mastermindgame.core.StateManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GameScreenTest {
    @Test
    fun allGreen() {
        val res = StateManager.evaluateGuess("ABCD", "ABCD")
        assertEquals(listOf(GREEN, GREEN, GREEN, GREEN), res)
        assertTrue(StateManager.isWin(res))
    }

    @Test
    fun allRed() {
        val res = StateManager.evaluateGuess("ABCD", "WXYZ")
        assertEquals(listOf(RED, RED, RED, RED), res)
    }

    @Test
    fun allOrangeReordered() {
        val res = StateManager.evaluateGuess("ABCD", "BCDA")
        assertEquals(listOf(ORANGE, ORANGE, ORANGE, ORANGE), res)
    }

    @Test
    fun duplicatesHandled() {
        val res = StateManager.evaluateGuess("AABC", "AAAA")
        assertEquals(listOf(GREEN, GREEN, RED, RED), res)
    }

    @Test
    fun mixedCase() {
        val res = StateManager.evaluateGuess("AABC", "ABAA")
        assertEquals(listOf(GREEN, ORANGE, ORANGE, RED), res)
    }
}