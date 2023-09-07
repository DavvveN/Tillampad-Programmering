package com.example.taskmanager

import androidx.compose.foundation.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.ui.theme.TaskManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    mainView(
                        stringResource(id = R.string.completed),
                        stringResource(id = R.string.nice_work),
                        Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun mainView(s1: String, s2: String, modifier: Modifier) {
    val image = painterResource(id = R.drawable.ic_task_completed)
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = image,
            contentDescription = null
        )

        Text(
            text = s1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(0.dp, 24.dp, 0.dp, 8.dp)
        )

        Text(
            text = s2,
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TaskManagerTheme {
        mainView(
            stringResource(id = R.string.completed),
            stringResource(id = R.string.nice_work),
            Modifier
        )

    }
}