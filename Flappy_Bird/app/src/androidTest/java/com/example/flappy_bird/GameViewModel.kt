package com.example.flappy_bird

import android.app.Application
import android.graphics.RectF
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.lang.Float.max
import kotlin.random.Random

// Enum that manage game status.
enum class GameStatus {
    WAITING,
    RUNNING,
    OVER,
}

const val BirdSizeWidthModel = 40f
const val BirdSizeHeightModel = BirdSizeWidthModel*7/10
const val GameScreenHeight = 500f
// don't show the bird at the very edge of the screen
const val ViewPortOffset = BirdSizeHeightModel*2
const val DistanceBetweenPipes = BirdSizeWidthModel*7
const val PipeWidth = BirdSizeWidthModel*1.5f
const val GapBetweenPipes = BirdSizeHeightModel*7
const val HorizontalSpeed = DistanceBetweenPipes

data class GameState (
    val birdState: BirdState = BirdState(RectF()),
    val pipes: List<PipePair> = emptyList(),
    val status: GameStatus = GameStatus.WAITING
) {
    fun distance(): Float {
        return birdState.position.left
    }
}

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private var prevTime = 0L // time of last frame update
    private var lastLift = 0L
    private var lastPipeStart = 0f
    private var wasTapped = false
    private var pipes = mutableListOf<PipePair>()
    private val _gameState = MutableStateFlow(GameState())
    val gameState = _gameState.asStateFlow()

    init {
        resetGame()
    }

    private fun resetGame() {
        lastPipeStart = 0f
        pipes.clear()
        repeat (10) {
            pipes.add(newPipePair())
        }
        _gameState.value = GameState(
            BirdState(RectF(0f, 200f, BirdSizeWidthModel,200 + BirdSizeHeightModel)),
            pipes.toList(),
            GameStatus.WAITING,
        )
    }

    private fun newPipePair(): PipePair {
        // between 100 and 300
        val gapStart = Random.nextFloat()*200+100
        val nextPipeStart = lastPipeStart + GapBetweenPipes
        val pair = PipePair(
            PipeState(RectF(nextPipeStart, 0f, nextPipeStart + PipeWidth, gapStart)),
            PipeState(RectF(nextPipeStart, gapStart + GapBetweenPipes, nextPipeStart + PipeWidth, GameScreenHeight))
        )
        lastPipeStart = nextPipeStart
        return pair
    }

    fun onTap() {
        // its good to keep button handlers small so they don't hold up the interface
        // therefore we just set a boolean and handle that logic in the game loop.
        // It also avoids any concurrency issues, all the movement of the bird happens in the game loopx
        wasTapped = true
    }

    private fun handleWaiting(frameTime: Long) {
        if (wasTapped) {
            resetGame()
            _gameState.value = _gameState.value.copy(status = GameStatus.RUNNING)
            wasTapped = false
            // So the bird stays idle for a short time
            lastLift = frameTime - MillisForIdle
        }
    }

    private fun handleGameOver(frameTime: Long) {
        if (wasTapped) {
            handleWaiting(frameTime)
        }
    }

    private fun endGame() {
        wasTapped = false
        _gameState.value = _gameState.value.copy(status = GameStatus.OVER)
    }

    private fun handleGameLoop(frameTime: Long, fractionalSeconds: Float) {
        // We take a copy of this because all the offset functions modify the rect, we don't want the UI
        // to get these partial updates, so we take a copy then update the GameState in a single replacement
        val birdPosition = RectF(_gameState.value.birdState.position)
        // Use an iterator so we can remove items as we go
        val iterator = pipes.listIterator()
        // If a pipe has gone off the screen we generate a new one to keep 10 pipes in our list
        // as we can't add items to an list while iterating we have to have a separate list for that
        val newPipes = mutableListOf<PipePair>()
        while(iterator.hasNext()) {
            val pipePair = iterator.next()
            when {
                // These pipes are out of view so remove them and add new
                pipePair.top.position.right < _gameState.value.distance() - ViewPortOffset -> {
                    iterator.remove()
                    newPipes.add(newPipePair())
                }
                // Pipes are sorted so all the remaining ones are further away than the bird
                // so we don't need to check any more
                pipePair.top.position.left >  _gameState.value.birdState.position.right -> {
                    break
                }
                else -> {
                    // RectF.intersects returns true if the two rectangles overlap, as all our pipes and the bird are RectF
                    // we can use this to figure out if the bird crashed
                    // As the previous if statements caught any pipes to the left or right of the bird this check only occurs
                    // for the pipe near our bird
                    if (RectF.intersects(pipePair.top.position, birdPosition) || RectF.intersects(pipePair.bottom.position,birdPosition)) {
                        endGame()
                        return
                    }
                }
            }
        }
        pipes.addAll(newPipes)

        // check if we crashed into ground
        if (birdPosition.bottom > GameScreenHeight) {
            endGame()
            return
        }

        birdPosition.offset(fractionalSeconds * HorizontalSpeed, 0f)
        var newAction: BirdAction
        if (wasTapped) {
            // don't let it fly out of the screen as otherwise you could fly over the pipes as they dont' extend infinitely up
            // we use max() to make it so you just bump the top, you either move up the standard amount or the distance to teh top of the screen
            // its max() because the amount is negative, so max is actually giving us the "smallest" amount of lift
            birdPosition.offset(0f, max(BirdLiftAmount, -birdPosition.top))
            newAction = BirdAction.FLAPPING
            wasTapped = false
            // we record lastLift because we need to start the bird falling if its been a long time since the last input
            lastLift = frameTime
        } else {
            // if we didn't just go up then we want to start falling
            // potentially fastFalling if its a long time since we flapped
            val millisSinceLift = frameTime - lastLift
            newAction = when {
                millisSinceLift > MillisForFastFall -> BirdAction.FAST_FALLING
                millisSinceLift > MillisForFalling -> BirdAction.FALLING
                millisSinceLift > MillisForIdle -> BirdAction.IDLE
                else -> _gameState.value.birdState.action
            }
            val heightDecrease = when(newAction) {
                BirdAction.FAST_FALLING -> BirdFastFallingSpeed
                else -> BirdFallingSpeed
            }*fractionalSeconds
            birdPosition.offset(0f, heightDecrease)
        }
        // Update the UI state to have the new list of pipes and bird position
        _gameState.value = _gameState.value.copy(
            birdState = BirdState(birdPosition, newAction),
            pipes = pipes.toList(),
        )
    }

    fun update(time: Long) {
        val fractionalSeconds = (time - prevTime).toFloat()/1000
        prevTime = time

        when (_gameState.value.status) {
            GameStatus.WAITING -> handleWaiting(time)
            GameStatus.RUNNING -> handleGameLoop(time, fractionalSeconds)
            GameStatus.OVER -> handleGameOver(time)
        }
    }
}