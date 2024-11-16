package com.bhandari.wave_progress

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import kotlin.also
import kotlin.apply
import kotlin.math.PI

enum class WaveDirection { RIGHT, LEFT }

/**
 * Draws a wave progress indicator.
 *
 * @param modifier Modifier to be applied to the layout.
 * @param progress The vertical progress of the wave.
 * @param fillBrush or color The brush or color to fill the wave.
 * @param amplitudeRange Highest and lowest point of wave to animate.
 * @param amplitudeDuration Duration it takes for wave to go from lowest to highest amplitude and vice versa
 * @param waveSteps number of points which will be drawn on path to generate sine wave across width of component, lesser the steps, more soft wave would be. More the steps, boxy the wave would be. Number can be tweaked as per performance requirement.
 * @param phaseShiftDuration determines speed of wave moving horizontally
 * @param waveDirection left or right horizontal movement of sine wave
 */
@Composable
fun WaveProgress(
    modifier: Modifier = Modifier,
    progress: Float,
    fillBrush: Brush? = Brush.horizontalGradient(listOf(Color.Magenta, Color.Cyan)),
    color: Color? = null,
    amplitudeRange: ClosedFloatingPointRange<Float> = 30f..50f,
    waveSteps: Int = 20,
    waveFrequency: Int = 3,
    phaseShiftDuration: Int = 2000,
    amplitudeDuration: Int = 2000,
    waveDirection: WaveDirection = WaveDirection.RIGHT
) {
    val path = remember { Path() } //reusing same path object to reduce object creation and gc calls
    val coroutineScope = rememberCoroutineScope()
    val phaseShift = remember { Animatable(0f) }
    val amplitude = remember { Animatable(amplitudeRange.start) }

    LaunchedEffect(amplitudeRange, amplitudeDuration) {
        coroutineScope.launch {
            amplitude.stop()
            amplitude.snapTo(amplitudeRange.start)
            amplitude.animateTo(
                targetValue = amplitudeRange.endInclusive,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = amplitudeDuration, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }
    }

    LaunchedEffect(phaseShiftDuration) {
        coroutineScope.launch {
            phaseShift.stop()
            phaseShift.snapTo(0f)
            phaseShift.animateTo(
                targetValue = (2 * PI).toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = phaseShiftDuration, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    Box(
        modifier = modifier
            .drawBehind {
                val yPos = (1 - progress) * size.height

                path
                    .apply {
                        reset()
                        val phaseShiftLocal = when (waveDirection) {
                            WaveDirection.RIGHT -> -phaseShift.value
                            WaveDirection.LEFT -> phaseShift.value
                        }
                        prepareSinePath(this, size, waveFrequency, amplitude.value, phaseShiftLocal, yPos, waveSteps)
                        lineTo(size.width, size.height)
                        lineTo(0f, size.height)
                        close()
                    }
                    .also {
                        if (fillBrush != null) {
                            drawPath(path = it, brush = fillBrush, style = Fill)
                        } else if (color != null) {
                            drawPath(path = it, color = color, style = Fill)
                        } else {
                            throw IllegalArgumentException("Either fillBrush or color must be provided")
                        }
                    }
            }
    )
}

@Preview
@Composable
fun WaveProgressPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        WaveProgress(progress = 0.5f, modifier = Modifier.fillMaxSize())
    }
}