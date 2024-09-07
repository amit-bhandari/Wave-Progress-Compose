package com.bhandari.progressbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.sin

@Composable
fun WaveProgress(progress: Float, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .drawBehind {
                val sinePath = Path()
                val amplitude = 50f
                val phaseShift = 1.5f
                val position = size.height / 2 //(1 - progress) * size.height
                val frequency = 2

                prepareSinePath(sinePath, size.width, frequency, amplitude, phaseShift, position)

                drawPath(
                    path = sinePath,
                    color = Color.Blue,
                    style = Stroke(width = 4f)
                )
            }
    )
}

fun prepareSinePath(
    path: Path,
    width: Float,
    frequency: Int,
    amplitude: Float,
    phaseShift: Float,
    position: Float
) {
    val midY = position//(1 - phaseShift) * height

    // Starting point of the sine wave
    path.moveTo(0f, midY)

    // Create the sine wave by iterating over the width of the canvas
    for (x in 0..width.toInt() step 10) {
        val y = midY + (amplitude * sin((x * frequency * Math.PI / width + phaseShift * 100).toFloat())).toFloat()
        path.lineTo(x.toFloat(), y)
    }
}


@Preview
@Composable
fun WaveProgressPreview() {
    WaveProgress(progress = 0.5f, modifier = Modifier.fillMaxSize())
}