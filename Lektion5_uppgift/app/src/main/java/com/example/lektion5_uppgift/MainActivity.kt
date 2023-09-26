package com.example.lektion5_uppgift

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lektion5_uppgift.ui.theme.Lektion5_uppgiftTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lektion5_uppgiftTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    initMainView(Modifier)
                }
            }
        }
    }
}

@Composable
fun initMainView(modifier: Modifier) {
    val image = painterResource(id = R.drawable.dav)

    var imageSize by remember {
        mutableStateOf(150)
    }
    var icon by remember {
        mutableStateOf(R.drawable.rocket_256x256)
    }
    var switch by remember {
        mutableStateOf(false)
    }

    val imageModifier = Modifier
        .size(imageSize.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = imageModifier
        )

        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(onClick = {
                imageSize += 5
                switch = !switch
                icon =
                    if (!switch) R.drawable.rocket_256x256 else R.drawable.icon_arrow_right_256x256
            }) {
                Text(stringResource(id = R.string.btn_text))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Lektion5_uppgiftTheme {
        initMainView(Modifier)
    }
}