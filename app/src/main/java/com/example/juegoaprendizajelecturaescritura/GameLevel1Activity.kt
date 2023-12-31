package com.example.juegoaprendizajelecturaescritura

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class GameLevel1Activity : ComponentActivity() {
    private val playerInfoActivity1Completed = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameLevel1()
        }
    }

    @Composable
    fun Bubble(
        letter: String,
        onBubblePopped: () -> Unit,
        bubbleDrawableMap: Map<String, Int>
    ) {
        val drawableId = bubbleDrawableMap[letter] ?: error("No drawable found for letter $letter")

        Image(
            painter = painterResource(id = drawableId),
            contentDescription = "Bubble $letter",
            modifier = Modifier
                .size(60.dp)
                .clickable { onBubblePopped() }
        )
    }

    @Composable
    fun LevelCompletionDialog(onDismissRequest: () -> Unit, onAdvanceClick: () -> Unit) {
        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            text = {
                // Colocamos la imagen del trofeo dentro del bloque text
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.trofeo),
                        contentDescription = "Trofeo",
                        modifier = Modifier
                            .size(100.dp)
                    )
                }
            },
            confirmButton = {
                Button(onClick = { onAdvanceClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(Color(0xFF8B4513))
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFB4513),
                        contentColor = Color.White
                    )) {
                    Text("Avanzar")
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Avanzar"
                    )
                }
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }


    @Composable
    fun GameLevel1() {
        // Lista de burbujas
        val bubbles = listOf("A", "B", "E", "J", "A")

// Diccionario
        val soundResources = mapOf(
            "A" to R.raw.nivel1_1,
            "B" to R.raw.nivel1_2,
            "E" to R.raw.nivel1_3,
            "J" to R.raw.nivel1_4,
            "A" to R.raw.nivel1_5
        )

        val bubbleDrawableMap = mapOf(
            "A" to R.drawable.burbuja_a,
            "B" to R.drawable.burbuja_b,
            "E" to R.drawable.burbuja_e,
            "J" to R.drawable.burbuja_j
        )

        var poppedBubbles by remember { mutableIntStateOf(0) }
        var showCompletionDialog by remember { mutableStateOf(false) }
        var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

// Reproducir el sonido correspondiente cuando se revienta una burbuja
        val mContext = LocalContext.current

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.bosque_abeja),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Mostrar las burbujas en una columna
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(bubbles.size) { index ->
                        Bubble(
                            letter = bubbles[index],
                            onBubblePopped = {
                                mediaPlayer?.release() // Liberar el MediaPlayer anterior si existe

                                // Crear un nuevo MediaPlayer para el sonido actual
                                mediaPlayer = MediaPlayer.create(mContext, soundResources.getValue(bubbles[index])).apply {
                                    start()
                                    setOnCompletionListener {
                                        release() // Liberar el MediaPlayer cuando el sonido ha terminado
                                    }
                                }
                                poppedBubbles++

                                // Cuando se revientan todas las burbujas
                                if (poppedBubbles == bubbles.size) {
                                    showCompletionDialog = true
                                }
                            },
                            bubbleDrawableMap = bubbleDrawableMap
                        )
                    }
                }
            }
            if (showCompletionDialog) {
                LevelCompletionDialog(
                    onDismissRequest = { showCompletionDialog = false },
                    onAdvanceClick = {
                        showCompletionDialog = false
                        playerInfoActivity1Completed.launch(Intent(this@GameLevel1Activity, PlayerInfoActivity1Completed::class.java))
                    }
                )
            }
        }
    }
}


