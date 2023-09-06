package com.example.composearticle

import androidx.compose.foundation.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composearticle.ui.theme.ComposeArticleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeArticleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    mainView(
                        stringResource(R.string.title),
                        stringResource(R.string.info1), stringResource(R.string.info2), modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun mainView(title: String, info: String, info2: String, modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.bg_compose_background)
    Column (

        modifier = Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

    ){

        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = title,
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            modifier = Modifier
                .padding(12.dp)
        )

        Text(
            text = info,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .padding(12.dp)
            )

        Text(
            text = info2,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .padding(12.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeArticleTheme {
        mainView(
            stringResource(R.string.title),
            stringResource(R.string.info1), stringResource(R.string.info2), modifier = Modifier
        )
    }
}