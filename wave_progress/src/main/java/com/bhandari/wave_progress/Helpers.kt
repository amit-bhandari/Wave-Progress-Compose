package com.bhandari.wave_progress

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import kotlin.math.min
import kotlin.math.sin

fun prepareSinePath(
    path: Path,
    size: Size,
    frequency: Int,
    amplitude: Float,
    phaseShift: Float,
    position: Float,
    step: Int
) {
    for (x in 0..size.width.toInt() step step) {
        val y = position + amplitude * sin(x * frequency * Math.PI / size.width + phaseShift).toFloat()
        if (path.isEmpty)
            path.moveTo(x.toFloat(), min(y, size.height))
        else
            path.lineTo(x.toFloat(), min(y, size.height))
    }
}