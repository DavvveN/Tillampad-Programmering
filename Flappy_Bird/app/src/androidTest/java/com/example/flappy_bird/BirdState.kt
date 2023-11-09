package com.example.flappy_bird

import android.graphics.RectF


data class BirdState(
    val position: RectF,
    val action: BirdAction = BirdAction.IDLE,
)

// Enum that manage game status.
enum class BirdAction {
    IDLE,
    FLAPPING,
    FALLING,
    FAST_FALLING,
    DEAD,
}

const val BirdLiftAmount = -BirdSizeHeightModel * 1.5f
const val BirdFallingSpeed = -BirdLiftAmount * .75f
const val BirdFastFallingSpeed = BirdFallingSpeed * 4.5f
const val MillisForIdle = 150
const val MillisForFalling = MillisForIdle + 100
const val MillisForFastFall = MillisForFalling + 100