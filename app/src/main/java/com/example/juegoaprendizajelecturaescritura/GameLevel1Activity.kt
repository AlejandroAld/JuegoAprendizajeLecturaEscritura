package com.example.juegoaprendizajelecturaescritura

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController

class GameLevel1Activity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameLevel1()
        }
    }

    @Composable
    fun Bubble(
        letter: String,
        onBubblePopped: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color.Blue)
                .clickable {
                    onBubblePopped()
                }
        ) {
            Text(
                text = letter,
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }
    }

    @Composable
    fun GameLevel1() {
        val bubbles = listOf("A", "B", "E", "J", "A")

// Diccionario
        val soundResources = mapOf(
            "A" to R.raw.nivel1_1,
            "B" to R.raw.nivel1_2,
            "E" to R.raw.nivel1_3,
            "J" to R.raw.nivel1_4,
            "A" to R.raw.nivel1_5
        )

        var poppedBubbles by remember { mutableStateOf(0) }

// Reproducir el sonido correspondiente cuando se revienta una burbuja
        val mContext = LocalContext.current
        val mediaPlayer = MediaPlayer.create(mContext, soundResources.getValue(bubbles[poppedBubbles]))

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
                                // al reventar una burbuja se reproduce su audio
                                mediaPlayer.start()
                                poppedBubbles++

                                // Cuando se revientan todas las burbujas se llama a onLevelCompleted
                                if (poppedBubbles == bubbles.size) {
                                    // Hacer un intent para ir a la pantalla de nivel completado
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


