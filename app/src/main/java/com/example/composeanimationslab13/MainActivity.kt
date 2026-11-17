package com.example.composeanimationslab13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
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
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { currentExercise = 1 },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (currentExercise == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Ej 1")
            }

            Button(
                onClick = { currentExercise = 2 },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (currentExercise == 2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Ej 2")
            }

            Button(
                onClick = { currentExercise = 3 },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (currentExercise == 3) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Ej 3")
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
            }
        }
    }
}