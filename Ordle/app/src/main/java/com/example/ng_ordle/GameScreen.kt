package com.example.ng_ordle


import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun GameScreen(
    possibleTargetWords: List<String>,
    validGuesses: Set<String>,
    numberOfLetters : Int,
    modifier: Modifier = Modifier,
    ordleViewModel: OrdleViewModel = viewModel(factory = OrdleViewModelFactory(validGuesses, numberOfLetters,possibleTargetWords))
) {
    val focusRequester = remember { FocusRequester() }
    val gameUiState by ordleViewModel.uiState.collectAsState()
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(5.dp)
    ) {
        // First show the guesses
        repeat(gameUiState.guesses.size) { index ->
            Guess(gameUiState.guesses[index], length = gameUiState.wordLength)
        }
        if (gameUiState.guesses.size < gameUiState.maxGuesses) {
            // Auto focus on the input boxes
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
            Guess(
                value = ordleViewModel.currentGuess,
                enabled = true,
                length = gameUiState.wordLength,
                onValueChange = { ordleViewModel.updateGuess(it) },
                keyboardActions = KeyboardActions {
                    ordleViewModel.submitGuess()
                },
                modifier = Modifier.focusRequester(focusRequester))
            if (ordleViewModel.currentGuessError.isNotEmpty()) {
                Text(
                    text = ordleViewModel.currentGuessError,
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                )
            }
        }
        // then show empty guesses (at most 5, if no guess was made)
        repeat(gameUiState.maxGuesses - 1 - gameUiState.guesses.size) {
            Guess(length = gameUiState.wordLength)
        }
        if (gameUiState.isGameOver) {
            EndGameDialog(
                didWin = gameUiState.didWin,
                guessesUsed = gameUiState.guesses.size,
                onPlayAgain = { ordleViewModel.resetGame() },
                targetWord = gameUiState.targetWord,
            )
        }
    }
}



@Composable
private fun EndGameDialog(
    guessesUsed: Int,
    didWin: Boolean,
    targetWord: String,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(
        onDismissRequest = {
            // Don't let it be dismissed by clicking outside the dialog
        },
        title = { Text(text = "Game over") },
        text = { Text(text = if (didWin) "You won in $guessesUsed tries" else "You lost. Word was $targetWord" ) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = "quit")
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = "new game")
            }
        }
    )
}
