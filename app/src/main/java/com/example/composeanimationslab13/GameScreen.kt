package com.example.composeanimationslab13


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.abs

enum class GameState { PLAYING, WON, LOST }

data class Point(val x: Int, val y: Int)

@Composable
fun GameScreen() {
    var level by remember { mutableStateOf(1) }
    var gameState by remember { mutableStateOf(GameState.PLAYING) }
    var player by remember { mutableStateOf(Point(0, 0)) }
    var coins by remember { mutableStateOf(generateCoins(1)) }
    var enemies by remember { mutableStateOf(generateEnemies(1)) }

    val bgColor by animateColorAsState(
        targetValue = when (gameState) {
            GameState.PLAYING -> Color(0xFF2C3E50)
            GameState.WON -> Color(0xFF27AE60)
            GameState.LOST -> Color(0xFFC0392B)
        },
        animationSpec = tween(1000)
    )

    LaunchedEffect(gameState, level) {
        if (gameState == GameState.PLAYING) {
            while (true) {
                delay(800)
                enemies = enemies.map { enemy ->
                    val dx = player.x.compareTo(enemy.x)
                    val dy = player.y.compareTo(enemy.y)
                    if (abs(player.x - enemy.x) > abs(player.y - enemy.y)) {
                        enemy.copy(x = enemy.x + dx)
                    } else {
                        enemy.copy(y = enemy.y + dy)
                    }
                }
                if (enemies.any { it.x == player.x && it.y == player.y }) {
                    gameState = GameState.LOST
                }
            }
        }
    }

    LaunchedEffect(player) {
        if (gameState == GameState.PLAYING) {
            val collected = coins.filter { it.x == player.x && it.y == player.y }
            if (collected.isNotEmpty()) {
                coins = coins - collected.toSet()
                if (coins.isEmpty()) {
                    if (level < 4) {
                        level++
                        player = Point(0, 0)
                        coins = generateCoins(level)
                        enemies = generateEnemies(level)
                    } else {
                        gameState = GameState.WON
                    }
                }
            }
            if (enemies.any { it.x == player.x && it.y == player.y }) {
                gameState = GameState.LOST
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(bgColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        AnimatedContent(targetState = gameState, label = "") { state ->
            when (state) {
                GameState.PLAYING -> Text("Nivel $level - Monedas: ${coins.size}", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
                GameState.WON -> Text("¡Has Escapado con el Tesoro!", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
                GameState.LOST -> Text("¡Atrapado por los Enemigos!", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.size(320.dp).background(Color(0xFF34495E))
        ) {
            coins.forEach { coin ->
                Box(
                    modifier = Modifier
                        .offset(x = (coin.x * 40).dp, y = (coin.y * 40).dp)
                        .size(40.dp)
                        .padding(10.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF1C40F))
                )
            }

            enemies.forEach { enemy ->
                val animX by animateDpAsState(targetValue = (enemy.x * 40).dp, tween(400), label = "")
                val animY by animateDpAsState(targetValue = (enemy.y * 40).dp, tween(400), label = "")
                Box(
                    modifier = Modifier
                        .offset(x = animX, y = animY)
                        .size(40.dp)
                        .padding(4.dp)
                        .background(Color(0xFFE74C3C))
                )
            }

            val playerX by animateDpAsState(targetValue = (player.x * 40).dp, tween(150), label = "")
            val playerY by animateDpAsState(targetValue = (player.y * 40).dp, tween(150), label = "")

            Box(
                modifier = Modifier
                    .offset(x = playerX, y = playerY)
                    .size(40.dp)
                    .padding(6.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF3498DB))
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        AnimatedVisibility(
            visible = gameState == GameState.PLAYING,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { if (player.y > 0) player = player.copy(y = player.y - 1) }) { Text("W") }
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(onClick = { if (player.x > 0) player = player.copy(x = player.x - 1) }) { Text("A") }
                    Button(onClick = { if (player.x < 7) player = player.copy(x = player.x + 1) }) { Text("D") }
                }
                Button(onClick = { if (player.y < 7) player = player.copy(y = player.y + 1) }) { Text("S") }
            }
        }

        AnimatedVisibility(visible = gameState != GameState.PLAYING) {
            Button(onClick = {
                level = 1
                player = Point(0, 0)
                coins = generateCoins(1)
                enemies = generateEnemies(1)
                gameState = GameState.PLAYING
            }) {
                Text("Jugar de Nuevo")
            }
        }
    }
}

fun generateCoins(level: Int): List<Point> {
    return (1..level + 2).map { Point((1..7).random(), (1..7).random()) }
}

fun generateEnemies(level: Int): List<Point> {
    return (1..level).map { Point((4..7).random(), (4..7).random()) }
}