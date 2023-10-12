package com.example.ng_ordle

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// To pass parameters in to a viewmodel you must use a factory to create it
// https://stackoverflow.com/questions/67982230/jetpack-compose-pass-parameter-to-viewmodel/67982296#67982296
class OrdleViewModelFactory(
    private val validWords: Set<String>,
    private val wordLength: Int,
    private val possibleTargetWords: List<String>) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = OrdleViewModel(validWords, possibleTargetWords,wordLength) as T
}

data class GameUiState(
    val guesses: List<List<Guess>> = emptyList(),
    val targetWord: String = "",
    val isGameOver: Boolean = false,
    val didWin: Boolean = false,
    val wordLength: Int,
    val maxGuesses: Int,
)

class OrdleViewModel(
    private val validWords: Set<String>,
    private val possibleTargetWords: List<String>,
    val wordLength: Int,
    private val maxGuesses: Int = 6,
) : ViewModel() {
    private lateinit var word: String
    private val _uiState = MutableStateFlow(GameUiState(wordLength = wordLength, maxGuesses = maxGuesses))
    private val guesses = mutableListOf<List<Guess>>()
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    var currentGuess by  mutableStateOf("")
        private set
    var currentGuessError by  mutableStateOf("")
        private set

    init {
        resetGame()
    }

    fun resetGame() {
        updateGuess("")
        word = possibleTargetWords.random()
        Log.i("ovm", "ordet: " + word)
        guesses.clear()
        _uiState.value = GameUiState(wordLength = wordLength, maxGuesses = maxGuesses, targetWord = word)
    }

    fun submitGuess() {
        if (!validWords.contains(currentGuess) || guesses.size == maxGuesses ) return

        var unmatchedLetters = mutableListOf<Char>()
        // First get all matching letters, and compile a list of non matching
        val newGuess = List(wordLength) { i ->
            var character = Guess(currentGuess[i], GuessState.NOT_IN_WORD)
            if (currentGuess[i] == word[i]) {
                character.state = GuessState.CORRECT
            } else {
                unmatchedLetters.add(word[i])
            }
            character
        }

        var didWin = false
        if (unmatchedLetters.isEmpty()) {
            didWin = true
        }
        // iterating through guess again to figure out any wrong position letters
        // if guess exists in list of remaining correct letters
        // change it to wrong place and remove that letter from remaining correct
        newGuess.forEach { it ->
            if (it.state == GuessState.NOT_IN_WORD) {
                if(unmatchedLetters.remove(it.character)) {
                    it.state = GuessState.WRONG_POSITION
                }
            }
        }
        guesses.add(newGuess)
        _uiState.update { currentState ->
            currentState.copy(
                guesses = guesses,
                isGameOver = didWin || guesses.size == maxGuesses,
                didWin = didWin,
            )
        }
        updateGuess("")
    }

    fun updateGuess(guess: String): Unit {
        val filtered = guess.lowercase().filter { c -> c.isLetter() }
        if (filtered.length > wordLength) {
            return
        }
        currentGuessError = ""
        currentGuess = filtered
        if (currentGuess.length == wordLength && !validWords.contains(filtered)) {
            currentGuessError = "guess must be in dictionary"
        }
    }
}