package com.angdim.mastermindgame.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.angdim.mastermindgame.R

@Composable
fun MatchSummaryScreen(title: String, textColors: List<Color> = listOf(Gray, Gray), secret: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.padding(dimensionResource(R.dimen.spacer_16)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Column(Modifier.padding(dimensionResource(R.dimen.spacer_20)), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.kbc),
                    contentDescription = stringResource(id = R.string.app_name)
                )
                Spacer(Modifier.height(dimensionResource(R.dimen.spacer_48)))
                Text(buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            brush = Brush.linearGradient(
                                colors = textColors
                            )
                        )
                    ) {
                        append(title)
                    }
                }, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                Spacer(Modifier.height(dimensionResource(R.dimen.spacer_8)))
                Text(stringResource(R.string.secret_was, secret), color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(dimensionResource(R.dimen.spacer_16)))
                Button(onClick = onRetry) { Text(stringResource(R.string.retry_btn)) }
            }
        }
    }
}