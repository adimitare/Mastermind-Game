package com.angdim.mastermindgame.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay
import android.util.Log
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.angdim.mastermindgame.Constants.DELAY
import com.angdim.mastermindgame.Constants.REMAINING
import com.angdim.mastermindgame.core.CharacterStatus.GREEN
import com.angdim.mastermindgame.core.CharacterStatus.ORANGE
import com.angdim.mastermindgame.core.CharacterStatus.RED
import com.angdim.mastermindgame.core.CharacterStatus
import com.angdim.mastermindgame.core.StateManager
import com.angdim.mastermindgame.R

@Composable
fun GameScreen(onSuccess: (String) -> Unit, onTimeUp: (String) -> Unit) {
    var secret by remember { mutableStateOf(StateManager.provideSecret()) }
    var remaining by remember { mutableStateOf(REMAINING) }
    var guess by remember { mutableStateOf(charArrayOf(' ', ' ', ' ', ' ')) }
    var eval by remember { mutableStateOf(listOf<CharacterStatus>()) }
    val message by remember { mutableStateOf(formatGameDescriptionText() ) }

    // As per requirements print out the random string on the console for convenience
    Log.d("Mastermind", "Secret: $secret")

    LaunchedEffect(secret) {
        remaining = REMAINING
        while (remaining > 0) {
            delay(DELAY)
            remaining -= 1
        }
        if (remaining == 0) onTimeUp(secret)
    }

    Column(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.spacer_16))
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(stringResource(R.string.mastermind_title), style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
            Text(timeText(remaining), color = Color.Gray)
        }
        Spacer(Modifier.height(dimensionResource(R.dimen.spacer_12)))
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Column(Modifier.padding(dimensionResource(R.dimen.spacer_16)), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_12))) {
                    for (i in 0 until 4) {
                        CharacterBox(
                            valueState = remember { mutableStateOf("") },
                            color = eval.getOrNull(i)?.toColor() ?: MaterialTheme.colorScheme.surface,
                            onValueChange = { new ->
                                val c = new.uppercase().replace(Regex("[^A-Z]"), "").take(1)
                                guess[i] = c.firstOrNull() ?: ' '
                            }
                        )
                    }
                }
                Spacer(Modifier.height(dimensionResource(R.dimen.spacer_16)))
                Button(onClick = {
                    val guessStr = String(guess).replace(" ", "")
                    if (guessStr.length == 4) {
                        val res = StateManager.evaluateGuess(secret, guessStr)
                        eval = res
                        if (StateManager.isWin(res)) {
                            onSuccess(secret)
                        }
                    }
                }) { Text(stringResource(R.string.check_btn)) }
                Spacer(Modifier.height(dimensionResource(R.dimen.spacer_8)))
                Text(text = message, color = Color.Gray, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
private fun CharacterBox(
    valueState: MutableState<String>,
    color: Color,
    onValueChange: (String) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val bg = if (color == MaterialTheme.colorScheme.surface) MaterialTheme.colorScheme.surface else color
        Column(
            modifier = Modifier
                .size(dimensionResource(R.dimen.spacer_64))
                .clip(RoundedCornerShape(dimensionResource(R.dimen.spacer_12)))
                .background(bg),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = valueState.value,
                onValueChange = {
                    val up = it.uppercase().replace(Regex("[^A-Z]"), "").take(1)
                    valueState.value = up
                    onValueChange(up)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    imeAction = ImeAction.Done
                ),
                textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                modifier = Modifier
                    .size(dimensionResource(R.dimen.spacer_64))
            )
        }
    }
}

private fun timeText(sec: Int): String = "${sec / 60}:${(sec % 60).toString().padStart(2, '0')}"

private fun CharacterStatus.toColor(): Color = when (this) {
    GREEN -> Color.Green
    ORANGE -> Color(0xffffa500)
    RED -> Color.Red
}

private fun formatGameDescriptionText(): AnnotatedString {
    return buildAnnotatedString {
        withStyle(SpanStyle(color = GREEN.toColor())) { append("Green") }
        append(": correct place • ")
        withStyle(SpanStyle(color = Color(0xFFFFA500))) { append("Orange") }
        append(": in word wrong place • ")
        withStyle(SpanStyle(color = Color.Red)) { append("Red") }
        append(": not present")
    }
}