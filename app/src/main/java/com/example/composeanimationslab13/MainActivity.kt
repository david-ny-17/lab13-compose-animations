package com.example.composeanimationslab13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composeanimationslab13.ui.theme.ComposeAnimationsLab13Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAnimationsLab13Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    var currentExercise by remember { mutableStateOf(1) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val exercises = listOf(1, 2, 3, 4, 5)
            exercises.forEach { ex ->
                Button(
                    onClick = { currentExercise = ex },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentExercise == ex) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) { Text("Ej $ex") }
            }
        }

        HorizontalDivider()

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            when (currentExercise) {
                1 -> VisibilityAnimationScreen()
                2 -> ColorAnimationScreen()
                3 -> SizeAndPositionScreen()
                4 -> AnimatedContentScreen()
                5 -> GameScreen()
            }
        }
    }
}