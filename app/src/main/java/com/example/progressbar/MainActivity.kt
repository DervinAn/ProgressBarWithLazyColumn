package com.example.progressbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }
}

@Composable
fun MainContent() {
    var curItem by remember { mutableStateOf(0) }
    val totalItems = 100

    Box(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        contentAlignment = Alignment.BottomEnd // Align content to the bottom end
    ) {
        // Adjust the size of the Box to fit the CircularProgressBar
        Box(modifier = Modifier.size(120.dp)) {
            val percentage = curItem / totalItems.toFloat()
            CirculareProgressBar(percentage = percentage, number = curItem)
        }
    }

    LazyColumn(onItemVisible = { curItem = it })
}

@Composable
fun LazyColumn(onItemVisible: (Int) -> Unit) {
    LazyColumn {
        items(100) { index ->
            onItemVisible(index)
            Text(
                text = "Item $index",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            )
        }
    }
}

@Composable
fun CirculareProgressBar(
    percentage :Float,
    number:Int,
    fontSize: TextUnit = 20.sp,
    raduis: Dp = 50.dp,
    color: Color = Color.Green,
    strokeWidth:  Dp = 8.dp,
    animDuration:Int = 1000,
    animDelay:Int = 0
){
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )
    LaunchedEffect (key1 = true){
        animationPlayed = true
    }


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(raduis * 2f)
    ){

        Canvas(modifier = Modifier.size(raduis * 2f)) {
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * curPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(text = (curPercentage.value * number).toInt().toString(),
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )

    }

}




