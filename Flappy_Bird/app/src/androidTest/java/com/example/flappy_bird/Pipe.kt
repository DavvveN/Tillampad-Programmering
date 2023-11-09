package com.example.flappy_bird

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp


@Composable
fun Pipe(
    state: PipeState,
    viewPortStart: Float,
    gameUnitToDp: Dp,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.pipe_pillar),
        contentScale = ContentScale.FillBounds,
        contentDescription = null,
        modifier = modifier
            .requiredSize(gameUnitToDp * state.position.width(), gameUnitToDp * state.position.height())
            .offset(
                gameUnitToDp * (state.position.left-viewPortStart),
                gameUnitToDp * (state.position.top),
            )
    )
}
