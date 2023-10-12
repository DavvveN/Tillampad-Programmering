/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.ng_ordle

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class GuessState {
    CORRECT, WRONG_POSITION, NOT_IN_WORD
}

data class Guess(
    val character: Char,
    var state: GuessState,
)

@Composable
fun Guess(
    guess: List<Guess>? = null,
    value: String = "",
    modifier: Modifier = Modifier,
    boxWidth: Dp = 40.dp,
    boxHeight: Dp = 40.dp,
    length: Int,
    enabled: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    keyboardActions: KeyboardActions = KeyboardActions(),
    onValueChange: (String) -> Unit = {},
) {
    val spaceBetweenBoxes = 8.dp
    BasicTextField(modifier = modifier,
        value = value,
        singleLine = true,
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        onValueChange = onValueChange,
        decorationBox = {
            Row(
                Modifier.size(
                    width = (boxWidth + spaceBetweenBoxes) * length,
                    height = boxHeight
                ),
                horizontalArrangement = Arrangement.spacedBy(spaceBetweenBoxes),
            ) {
                repeat(length) { index ->
                    // If we don't have a guess we are just showing the value
                    val character = guess?.get(index)?.character ?: value.getOrNull(index) ?: ' '
                    val color = when (guess?.get(index)?.state ?: GuessState.NOT_IN_WORD) {
                        GuessState.CORRECT -> Color.Green
                        GuessState.WRONG_POSITION -> Color.Yellow
                        GuessState.NOT_IN_WORD -> Color.White
                    }
                    Box(
                        modifier = Modifier
                            .size(boxWidth, boxHeight)
                            .border(
                                1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .background(color),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = character.uppercase(),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        })
}