package com.example.composeanimationslab13

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

enum class UiState {
    LOADING, CONTENT, ERROR
}

@Composable
fun AnimatedContentScreen() {

    var currentState by remember { mutableStateOf(UiState.LOADING) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))


        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { currentState = UiState.LOADING }) { Text("Cargar") }
            Button(onClick = { currentState = UiState.CONTENT }) { Text("Éxito") }
            Button(onClick = { currentState = UiState.ERROR }) { Text("Error") }
        }

        Spacer(modifier = Modifier.height(48.dp))


        AnimatedContent(
            targetState = currentState,
            transitionSpec = {

                fadeIn(animationSpec = tween(durationMillis = 800)) togetherWith
                        fadeOut(animationSpec = tween(durationMillis = 800))
            },
            label = "stateAnimation"
        ) { state ->

            when (state) {
                UiState.LOADING -> {
                    Text(
                        text = "Cargando información...",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Gray
                    )
                }
                UiState.CONTENT -> {
                    Text(
                        text = "Contenido cargado con éxito",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF007A33), // Verde oscuro
                        fontWeight = FontWeight.Bold
                    )
                }
                UiState.ERROR -> {
                    Text(
                        text = "Ocurrió un error inesperado",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}