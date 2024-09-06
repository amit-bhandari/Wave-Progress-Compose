package com.bhandari.progressbar

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

@Composable
fun WaveProgress(progress: Float, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .drawBehind {
                println("Size $size")
                drawRect(
                    Color.Magenta,
                    topLeft = Offset(0f, 0f + size.height * (1 - progress)),
                    size = Size(size.width, size.height * progress)
                )
            }
    )
}
