package com.bhandari.progressbar

import android.util.Range
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
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun WaveProgress(
    progress: Float,
    modifier: Modifier = Modifier,
    amplitudeRange: Range<Float> = Range(20f, 80f),
    fillBrush: Brush = Brush.horizontalGradient(listOf(Color.Red, Color.Blue)),
    waveSteps: Int = 20,
    waveFrequency: Int = 2,
) {
    val phaseShift = remember { Animatable(0f) }
    val amplitude = remember { Animatable(amplitudeRange.lower) }

    // Coroutine scope for running concurrent animations
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            phaseShift.animateTo(
                targetValue = (2 * PI).toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 2000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        }

        coroutineScope.launch {
            amplitude.animateTo(
                targetValue = amplitudeRange.upper,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 2000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }
    }

    Box(
        modifier = modifier
            .drawBehind {
                val yPos = (1 - progress) * size.height

                Path()
                    .apply {
                        prepareSinePath(this, size.width, waveFrequency, amplitude.value, phaseShift.value, yPos, waveSteps)
                        lineTo(size.width, size.height)
                        lineTo(0f, size.height)
                        close()
                    }.also { drawPath(path = it, brush = fillBrush, style = Fill) }
            }
    )
}

fun prepareSinePath(
    path: Path,
    width: Float,
    frequency: Int,
    amplitude: Float,
    phaseShift: Float,
    position: Float,
    step: Int
) {
    for (x in 0..width.toInt() step step) {
        val y = position + amplitude * sin(x * frequency * Math.PI / width + phaseShift).toFloat()
        if (path.isEmpty)
            path.moveTo(x.toFloat(), y)
        else
            path.lineTo(x.toFloat(), y)
    }
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