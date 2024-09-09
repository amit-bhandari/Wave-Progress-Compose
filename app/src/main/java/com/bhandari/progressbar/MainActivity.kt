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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.bhandari.progressbar.ui.theme.ProgressBarTheme
import com.bhandari.wave_progress.WaveDirection
import com.bhandari.wave_progress.WaveProgress
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val currentProgress = MutableLiveData(0f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProgressBarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val progress by currentProgress.observeAsState(0f)
                    Activity(modifier = Modifier.padding(innerPadding), progress)
                }
            }
        }

        lifecycleScope.launch {
            while (true) {
                delay(30)
                if (currentProgress.value!! >= 1f)
                    currentProgress.value = 0f
                currentProgress.value = currentProgress.value?.plus(0.002f)
            }
        }
    }
}

@Composable
fun Activity(modifier: Modifier = Modifier, progress: Float) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
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
                    waveDirection = WaveDirection.LEFT
                )
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                Text("Progress", modifier = Modifier.align(Alignment.CenterVertically))
                Slider(
                    value = 0.5f,
                    onValueChange = { }
                )
            }

            Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                Text("Min Amplitude", modifier = Modifier.align(Alignment.CenterVertically))
                Slider(
                    value = 0.5f,
                    onValueChange = { }
                )
            }

            Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                Text("Max Amplitude", modifier = Modifier.align(Alignment.CenterVertically))
                Slider(
                    value = 0.5f,
                    onValueChange = { }
                )
            }

            Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                Text("Frequency", modifier = Modifier.align(Alignment.CenterVertically))
                Slider(
                    value = 0.5f,
                    onValueChange = { }
                )
            }

            Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                Text("Steps", modifier = Modifier.align(Alignment.CenterVertically))
                Slider(
                    value = 0.5f,
                    onValueChange = { }
                )
            }

            Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                Text("PhaseShift Duration", modifier = Modifier.align(Alignment.CenterVertically))
                Slider(
                    value = 0.5f,
                    onValueChange = { }
                )
            }

            Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                Text("Amplitude Duration", modifier = Modifier.align(Alignment.CenterVertically))
                Slider(
                    value = 0.5f,
                    onValueChange = { }
                )
            }

            Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                Text("Direction", modifier = Modifier.align(Alignment.CenterVertically))
                Switch(true, onCheckedChange = {

                })
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProgressBarTheme {
        Activity(progress = 1f)
    }
}