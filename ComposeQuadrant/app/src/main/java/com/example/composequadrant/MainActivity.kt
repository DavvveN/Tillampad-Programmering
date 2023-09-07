package com.example.composequadrant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composequadrant.ui.theme.ComposeQuadrantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeQuadrantTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    mainView(
                        t1 = stringResource(id = R.string.txt_composable),
                        i1 = stringResource(id = R.string.material_design_guide),
                        t2 = stringResource(id = R.string.row_composable),
                        i2 = stringResource(id = R.string.horizontal_sequence),
                        t3 = stringResource(id = R.string.image_composable),
                        i3 = stringResource(id = R.string.painter_class_object),
                        t4 = stringResource(id = R.string.column_composable),
                        i4 = stringResource(id = R.string.vertical_sequence),
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun mainView(
    t1: String, i1: String,
    t2: String, i2: String,
    t3: String, i3: String,
    t4: String, i4: String,
    modifier: Modifier
) {

    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.weight(1f)) {
            infoCard(
                title = t1,
                info = i1,
                backgroundColor = Color(0xFFEADDFF),
                modifier = Modifier.weight(1f)
            )
            infoCard(
                title = t2,
                info = i2,
                backgroundColor = Color(0xFFD0BCFF),
                modifier = Modifier.weight(1f)
            )
        }

        Row(Modifier.weight(1f)) {
            infoCard(
                title = t3,
                info = i3,
                backgroundColor = Color(0xFFB69DF8),
                modifier = Modifier.weight(1f)
            )
            infoCard(
                title = t4,
                info = i4,
                backgroundColor = Color(0xFFF6EDFF),
                modifier = Modifier.weight(1f)
            )

        }
    }

}

@Composable
private fun infoCard(
    title: String,
    info: String,
    backgroundColor: Color,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {
        Text(
            text = title,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold
        )

        Text(
            text = info,
            textAlign = TextAlign.Justify
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeQuadrantTheme {
        mainView(
            t1 = stringResource(id = R.string.txt_composable),
            i1 = stringResource(id = R.string.material_design_guide),
            t2 = stringResource(id = R.string.row_composable),
            i2 = stringResource(id = R.string.horizontal_sequence),
            t3 = stringResource(id = R.string.image_composable),
            i3 = stringResource(id = R.string.painter_class_object),
            t4 = stringResource(id = R.string.column_composable),
            i4 = stringResource(id = R.string.vertical_sequence),
            modifier = Modifier
        )
    }
}