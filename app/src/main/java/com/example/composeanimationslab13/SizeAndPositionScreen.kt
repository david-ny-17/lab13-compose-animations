package com.example.composeanimationslab13

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SizeAndPositionScreen() {

    var isMovedAndExpanded by remember { mutableStateOf(false) }

    val animatedSize by animateDpAsState(
        targetValue = if (isMovedAndExpanded) 200.dp else 100.dp,
        animationSpec = tween(durationMillis = 1000),
        label = "sizeAnimation"
    )

    val animatedOffsetY by animateDpAsState(
        targetValue = if (isMovedAndExpanded) 150.dp else 0.dp,
        animationSpec = tween(durationMillis = 1000),
        label = "offsetAnimation"
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { isMovedAndExpanded = !isMovedAndExpanded }) {
            Text(text = "Mover y Escalar")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .offset(y = animatedOffsetY)
                .size(animatedSize)
                .background(Color.Magenta)
        )
    }
}