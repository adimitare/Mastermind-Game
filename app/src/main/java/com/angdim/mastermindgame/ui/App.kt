package com.angdim.mastermindgame.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.angdim.mastermindgame.Constants.SECTET_ARG
import com.angdim.mastermindgame.R

@Composable
fun App() {
    val nav = rememberNavController()
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        NavHost(navController = nav, startDestination = stringResource(id = R.string.game_message)) {
            composable("game") {
                GameScreen(
                    onSuccess = { secret -> nav.navigate("success/$secret") },
                    onTimeUp = { secret -> nav.navigate("gameover/$secret") }
                )
            }
            composable("success/{secret}") { backStackEntry ->
                val secret = backStackEntry.arguments?.getString(SECTET_ARG).orEmpty()
                MatchSummaryScreen(title = stringResource(id = R.string.you_cracked_it), textColors = listOf(colorResource(R.color.green_success_light), colorResource(R.color.green_success_dark)), secret = secret, onRetry = {
                    nav.navigateUp()
                    nav.navigate("game")
                })
            }
            composable("gameover/{secret}") { backStackEntry ->
                val secret = backStackEntry.arguments?.getString(SECTET_ARG).orEmpty()
                MatchSummaryScreen(title = stringResource(id = R.string.times_up), secret = secret, onRetry = {
                    nav.navigateUp()
                    nav.navigate("game")
                })
            }
        }
    }
}

