package com.bhandari.wave_progress

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
import kotlin.also
import kotlin.apply
import kotlin.math.PI

@Composable
fun WaveProgress(
    modifier: Modifier = Modifier,
    progress: Float,
    fillBrush: Brush? = null,
    color: Color? = null,
    amplitudeRange: Range<Float> = Range(20f, 80f),
    waveSteps: Int = 20,
    waveFrequency: Int = 2,
    phaseShiftDuration: Int = 2000,
    amplitudeDuration: Int = 2000,
) {
    val coroutineScope = rememberCoroutineScope()
    val phaseShift = remember { Animatable(0f) }
    val amplitude = remember { Animatable(amplitudeRange.lower) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            phaseShift.animateTo(
                targetValue = (2 * PI).toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = phaseShiftDuration, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
        coroutineScope.launch {
            amplitude.animateTo(
                targetValue = amplitudeRange.upper,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = amplitudeDuration, easing = LinearEasing),
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