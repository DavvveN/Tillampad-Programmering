package com.example.ng_ordle

import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.ng_ordle.ui.theme.BajenGreen
import com.example.ng_ordle.ui.theme.NG_OrdleTheme
import java.io.BufferedReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NG_OrdleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BajenGreen
                ) {
                    var numb by remember {
                        mutableStateOf(-1)
                    }
                    var visablity by remember {
                        mutableStateOf(true)
                    }
                    if(visablity) {
                        Column {
                            repeat(3) {
                                var a = it + 4
                                Button(onClick = { numb = a }) {
                                    Text(a.toString())
                                }
                            }
                        }
                    }
                    if (numb!= -1){
                        val possibleWords = assets.open("ord$numb.txt").bufferedReader().use(BufferedReader::readText).lines()
                        val validGuesses = possibleWords.toSet()
                        visablity = false
                        GameScreen(possibleWords, validGuesses,numb)
                    }
                }
            }
        }
    }
}



