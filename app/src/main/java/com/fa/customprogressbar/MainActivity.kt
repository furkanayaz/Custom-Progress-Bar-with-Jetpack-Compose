package com.fa.customprogressbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fa.customprogressbar.ui.theme.BackgroundCircleColor
import com.fa.customprogressbar.ui.theme.BackgroundLineColor
import com.fa.customprogressbar.ui.theme.CustomProgressBarTheme
import com.fa.customprogressbar.ui.theme.ForegroundLineColor
import com.fa.customprogressbar.ui.theme.StrokeCircleColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomProgressBarTheme {
                MyCustomView()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCustomView() {

    val progressValue = remember {
        mutableStateOf(value = "")
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
        CustomProgressBar(
            backgroundLineColor = BackgroundLineColor,
            foregroundLineColor = ForegroundLineColor,
            backgroundCircleColor = BackgroundCircleColor,
            strokeLineWidth = 50.0f,
            strokeCircleColor = StrokeCircleColor,
            strokeCircleWidth = 8.0f,
            circleRadius = 35.0f,
            delayMillis = 0,
            durationMillis = 1000,
            progressValue = if (progressValue.value.isEmpty()) 0.0f else progressValue.value.toFloat(),
            maxProgressValue = 100.0f
        )
        OutlinedTextField(modifier = Modifier.fillMaxWidth().padding(start = 32.0.dp, end = 32.0.dp), placeholder = { Text(text = "Enter value...", style = TextStyle(color = Color.DarkGray)) }, value = progressValue.value, onValueChange = { changed: String -> progressValue.value = changed})
    }
}

@Preview
@Composable
private fun PreviewMyCustomView() {
    MyCustomView()
}