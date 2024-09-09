package com.bhandari.progressbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.bhandari.progressbar.ui.theme.ProgressBarTheme
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
                currentProgress.value = currentProgress.value?.plus(0.001f)
            }
        }
    }
}

@Composable
fun Activity(modifier: Modifier = Modifier, progress: Float) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        WaveProgress(
            progress = progress,
            modifier = Modifier.fillMaxWidth().weight(1f),
            fillBrush = Brush.horizontalGradient(listOf(Color.Magenta, Color.Cyan)),
        )

        Text("Captain", modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProgressBarTheme {
        Activity(progress = 1f)
    }
}