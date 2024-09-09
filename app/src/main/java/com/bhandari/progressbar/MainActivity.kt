package com.bhandari.progressbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bhandari.progressbar.ui.theme.ProgressBarTheme
import com.bhandari.wave_progress.WaveDirection
import com.bhandari.wave_progress.WaveProgress


class MainActivity : ComponentActivity() {
    //private val currentProgress = MutableLiveData(0f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProgressBarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Activity(modifier = Modifier.padding(innerPadding))
                }
            }
        }

        /*lifecycleScope.launch {
            while (true) {
                delay(30)
                if (currentProgress.value!! >= 1f)
                    currentProgress.value = 0f
                currentProgress.value = currentProgress.value?.plus(0.002f)
            }
        }*/
    }
}

@Composable
fun Activity(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        var progress by remember { mutableFloatStateOf(0.2f) }
        var minAmplitude by remember { mutableFloatStateOf(20f) }
        var maxAmplitude by remember { mutableFloatStateOf(50f) }
        var frequency by remember { mutableIntStateOf(3) }
        var steps by remember { mutableIntStateOf(20) }
        var phaseShiftDuration by remember { mutableIntStateOf(2000) }
        var amplitudeDuration by remember { mutableIntStateOf(2000) }
        var direction by remember { mutableStateOf(WaveDirection.RIGHT) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Card(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .border(5.dp, Color.White, CircleShape)
            ) {
                WaveProgress(
                    progress = progress,
                    modifier = Modifier.fillMaxSize(),
                    fillBrush = Brush.horizontalGradient(listOf(Color.Magenta, Color.Cyan)),
                    waveDirection = direction,
                    amplitudeRange = minAmplitude..maxAmplitude,
                    waveFrequency = frequency,
                    waveSteps = steps,
                    phaseShiftDuration = phaseShiftDuration,
                    amplitudeDuration = amplitudeDuration
                )
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                Text("Progress", modifier = Modifier.align(Alignment.CenterVertically))
                Slider(
                    value = progress,
                    onValueChange = { progress = it }
                )
            }

            Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                Text("Min Amplitude", modifier = Modifier.align(Alignment.CenterVertically))
                Slider(
                    value = minAmplitude,
                    onValueChange = { minAmplitude = it },
                    valueRange = 10f..40f
                )
            }

            Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                Text("Max Amplitude", modifier = Modifier.align(Alignment.CenterVertically))
                Slider(
                    value = maxAmplitude,
                    onValueChange = { maxAmplitude = it },
                    valueRange = 40f..80f
                )
            }

            Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                Text("Frequency", modifier = Modifier.align(Alignment.CenterVertically))
                Slider(
                    value = frequency.toFloat(),
                    onValueChange = { frequency = it.toInt() },
                    valueRange = 2f..10f,
                )
            }

            Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                Text("Steps", modifier = Modifier.align(Alignment.CenterVertically))
                Slider(
                    value = steps.toFloat(),
                    onValueChange = { steps = it.toInt() },
                    valueRange = 2f..100f,
                )
            }

            Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                Text("PhaseShift Duration", modifier = Modifier.align(Alignment.CenterVertically))
                Slider(
                    value = phaseShiftDuration.toFloat(),
                    onValueChange = { phaseShiftDuration = it.toInt() },
                    valueRange = 100f..5000f,
                )
            }

            Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                Text("Amplitude Duration", modifier = Modifier.align(Alignment.CenterVertically))
                Slider(
                    value = amplitudeDuration.toFloat(),
                    onValueChange = { amplitudeDuration = it.toInt() },
                    valueRange = 100f..5000f,
                )
            }

            Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                Text("Direction: Left ", modifier = Modifier.align(Alignment.CenterVertically))
                Switch(
                    direction == WaveDirection.RIGHT,
                    onCheckedChange = {
                        direction =
                            if (direction == WaveDirection.RIGHT) WaveDirection.LEFT
                            else WaveDirection.RIGHT
                    })
                Text(" Right", modifier = Modifier.align(Alignment.CenterVertically))

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProgressBarTheme {
        Activity()
    }
}