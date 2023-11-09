package com.example.flappy_bird

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flappy_bird.ui.theme.ForegroundEarthYellow
import com.example.flappy_bird.ui.theme.GroundDividerPurple

@Composable
fun GameScreen(
    viewModel: GameViewModel = viewModel()
) {
    val gameState by viewModel.gameState.collectAsState()
    var gameUnitToDp by remember { mutableStateOf(0.dp) }
    var screenWidthGameUnits by remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        while (true) {
            withFrameMillis {
                viewModel.update(it)
            }
        }
    }
    Column(
        modifier = Modifier
            .background(ForegroundEarthYellow)
            .clickable { viewModel.onTap() }
    ) {
        val localDensity = LocalDensity.current
        Box(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .onGloballyPositioned { coordinates ->
                with(localDensity) {
                    gameUnitToDp = coordinates.size.height.toDp() / GameScreenHeight
                    screenWidthGameUnits = coordinates.size.width.toDp() / gameUnitToDp
                }
            }
        ) {
            Background(Modifier.fillMaxSize())

            Text(
                gameState.distance().toInt().toString(),
                Modifier
                    .align(Alignment.TopCenter)
                    .zIndex(1f), // Draw on top of everything
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )

            if (gameState.status == GameStatus.OVER || gameState.status == GameStatus.WAITING) {
                Text(
                    "Tap to start",
                    Modifier
                        .align(Alignment.Center)
                        .zIndex(1f), // Draw on top of everything
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            val viewPortStart = gameState.distance() - ViewPortOffset
            Bird(state = gameState.birdState, viewPortStart, gameUnitToDp)
            for (pipePair in gameState.pipes) {
                // don't bother trying to draw anything which is offscreen
                if (pipePair.top.position.left > (screenWidthGameUnits + viewPortStart)) {
                    break
                }
                Pipe(pipePair.top, viewPortStart, gameUnitToDp)
                Pipe(pipePair.bottom, viewPortStart, gameUnitToDp)
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        ) {
            Ground()
        }
    }
}


@Composable
fun Background(modifier: Modifier = Modifier) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun Ground(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
    ) {
        // Divider between background and foreground
        Divider(
            color = GroundDividerPurple,
            thickness = 5.dp
        )

        Image(
            painter = painterResource(id = R.drawable.foreground_road),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.23f)
        )

    }
}
