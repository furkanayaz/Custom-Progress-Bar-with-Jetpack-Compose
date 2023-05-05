package com.fa.customprogressbar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fa.customprogressbar.ui.theme.BackgroundCircleColor
import com.fa.customprogressbar.ui.theme.BackgroundLineColor
import com.fa.customprogressbar.ui.theme.ForegroundLineColor
import com.fa.customprogressbar.ui.theme.StrokeCircleColor
import com.fa.customprogressbar.ui.theme.TextColor

@Composable
fun CustomProgressBar(
    backgroundLineColor: Color = BackgroundLineColor,
    foregroundLineColor: Color = ForegroundLineColor,
    backgroundCircleColor: Color = BackgroundCircleColor,
    strokeLineWidth: Float = 50.0f,
    strokeCircleColor: Color = StrokeCircleColor,
    strokeCircleWidth: Float = 10.0f,
    circleRadius: Float = 30.0f,
    delayMillis: Int = 0,
    durationMillis: Int = 1000,
    progressValue: Float = 0.0f,
    maxProgressValue: Float = 100.0f,
) {
    val reCalculatedProgress = if (maxProgressValue <= progressValue) maxProgressValue else progressValue

    val textEndPadding = remember {
        mutableStateOf(value = 0.0f)
    }

    val animationPlay = remember {
        mutableStateOf(value = false)
    }

    val animatedProgressValue = animateFloatAsState(targetValue = if (animationPlay.value) reCalculatedProgress * 10.0f else 80.0f, animationSpec = tween(delayMillis = delayMillis, durationMillis = durationMillis))

    LaunchedEffect(key1 = true) {
        animationPlay.value = true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.0.dp, bottom = 32.0.dp)
            .drawBehind {
                val calculatedWidth = (size.width / 100.0f).toInt() * 100
                val gapWidth = ((size.width) - calculatedWidth).toInt()

                textEndPadding.value = gapWidth.toFloat()

                drawBackgroundLine(
                    backgroundLineColor = backgroundLineColor,
                    strokeLineWidth = strokeLineWidth,
                    cap = StrokeCap.Round,
                    lineWidth = calculatedWidth,
                    gapWidth = gapWidth
                )
                drawForegroundLine(
                    foregroundLineColor = foregroundLineColor,
                    strokeLineWidth = strokeLineWidth,
                    cap = StrokeCap.Round,
                    progressValue = animatedProgressValue.value,
                    gapWidth = gapWidth
                )
                drawBackgroundCircle(
                    backgroundCircleColor = backgroundCircleColor,
                    progressValue = animatedProgressValue.value,
                    circleRadius = circleRadius
                )
                drawForegroundCircle(
                    strokeCircleColor = strokeCircleColor,
                    strokeCircleWidth = strokeCircleWidth,
                    progressValue = animatedProgressValue.value,
                    circleRadius = circleRadius
                )
            },
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(modifier = Modifier.fillMaxWidth().padding(top = 12.0.dp, end = (textEndPadding.value / 4.0f).dp),
                text = "${animatedProgressValue.value.toInt() / 10}/${maxProgressValue.toInt()}",
                style = TextStyle(fontSize = 14.0.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold, color = TextColor),
                textAlign = TextAlign.End)
    }
}

private fun DrawScope.drawBackgroundLine(
    backgroundLineColor: Color,
    strokeLineWidth: Float,
    cap: StrokeCap,
    lineWidth: Int,
    gapWidth: Int
) {

    drawLine(
        color = backgroundLineColor,
        start = Offset(x = gapWidth.toFloat(), y = 0.0f),
        end = Offset(x = lineWidth.toFloat(), y = 0.0f),
        strokeWidth = strokeLineWidth,
        cap = cap
    )
}

private fun DrawScope.drawForegroundLine(
    foregroundLineColor: Color,
    strokeLineWidth: Float,
    cap: StrokeCap,
    progressValue: Float,
    gapWidth: Int
) {

    drawLine(
        color = foregroundLineColor,
        start = Offset(x = gapWidth.toFloat(), y = 0.0f),
        end = Offset(x = if (progressValue <= 80.0f) 80.0f else progressValue, y = 0.0f),
        strokeWidth = strokeLineWidth,
        cap = cap
    )
}

private fun DrawScope.drawBackgroundCircle(
    backgroundCircleColor: Color,
    progressValue: Float,
    circleRadius: Float
) {
    drawCircle(
        color = backgroundCircleColor,
        radius = circleRadius,
        center = Offset(x = if (progressValue <= 80.0f) 80.0f else progressValue, y = 0.0f)
    )
}

private fun DrawScope.drawForegroundCircle(
    strokeCircleColor: Color,
    strokeCircleWidth: Float,
    progressValue: Float,
    circleRadius: Float
) {
    drawCircle(
        style = Stroke(width = strokeCircleWidth, cap = StrokeCap.Round),
        color = strokeCircleColor,
        radius = circleRadius,
        center = Offset(x = if (progressValue <= 80.0f) 80.0f else progressValue, y = 0.0f)
    )
}