package com.bhandari.wave_progress

import androidx.compose.ui.graphics.Path
import kotlin.math.sin

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