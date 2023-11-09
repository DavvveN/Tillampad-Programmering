package com.example.flappy_bird

import android.graphics.RectF


data class PipePair (
    val top: PipeState,
    val bottom: PipeState,
)

data class PipeState (
    val position: RectF,
)

