package com.example.businesscardapp

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscardapp.ui.theme.BusinessCardAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    initMainView()
                }
            }
        }
    }
}

@Composable
fun initMainView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        upperBox(
            fullName = stringResource(id = R.string.full_name),
            title = stringResource(id = R.string.title),
            modifier = Modifier
        )

        lowerBox(
            phoneNumber = stringResource(id = R.string.phone_number),
            socialmedia = stringResource(id = R.string.social_media),
            mail = stringResource(id = R.string.mail),
            modifier = Modifier
        )
    }
}

@Composable
fun upperBox(
    fullName: String,
    title: String,
    modifier: Modifier
) {
    val image = painterResource(id = R.drawable.dav)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(top = 60.dp, bottom = 30.dp)
    ) {
        val rainbowColorsBrush = remember {
            Brush.sweepGradient(
                listOf(
                    Color(0xFF9575CD),
                    Color(0xFFBA68C8),
                    Color(0xFFE57373),
                    Color(0xFFFFB74D),
                    Color(0xFFFFF176),
                    Color(0xFFAED581),
                    Color(0xFF4DD0E1),
                    Color(0xFF9575CD)
                )
            )
        }
        val borderWidth = 4.dp

        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .border(
                    BorderStroke(borderWidth,rainbowColorsBrush),
                    CircleShape
                )
                .clip(CircleShape)
        )

        Text(
            text = fullName,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(10.dp)
        )

        Text(
            text = title,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(10.dp)
        )
    }
}

@Composable
fun lowerBox(phoneNumber: String, socialmedia: String, mail: String, modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom,
    ) {
        textElement(s1 = phoneNumber,Icons.Rounded.Phone ,modifier = modifier)
        textElement(s1 = socialmedia,Icons.Rounded.Person ,modifier = modifier)
        textElement(s1 = mail,Icons.Rounded.Email ,modifier = modifier)

    }
}


@Composable
fun textElement(s1: String, icon: ImageVector, modifier: Modifier) {
    Row {

        Icon(
            icon,
            contentDescription = null,
            modifier = modifier
                .padding(10.dp)
        )
        Text(
            text = s1,
            fontSize = 16.sp,
            textAlign = TextAlign.Left,
            modifier = modifier
                .padding(10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BusinessCardAppTheme {
        initMainView()
    }
}