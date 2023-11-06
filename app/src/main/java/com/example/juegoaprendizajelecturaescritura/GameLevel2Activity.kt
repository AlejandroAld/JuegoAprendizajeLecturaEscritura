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
import androidx.compose.runtime.DisposableEffect
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

class GameLevel2Activity : ComponentActivity() {
    private val playerInfoActivity2Completed = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameLevel2()
        }
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
    fun GameLevel2() {
        var background by remember { mutableStateOf(R.drawable.fondo_game2) }
        var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
        var showCompletionDialog by remember { mutableStateOf(false) }
        val mContext = LocalContext.current

        // Manejar el ciclo de vida del MediaPlayer
        DisposableEffect(Unit) {
            onDispose {
                mediaPlayer?.release()
                mediaPlayer = null
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(id = R.drawable.sound_icon),
                contentDescription = "Play Sound",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        val soundResId = when (background) {
                            R.drawable.fondo_game2 -> R.raw.elefante
                            R.drawable.fondo_game2_1 -> R.raw.unelefante
                            R.drawable.fondo_game2_2 -> R.raw.doselefantes
                            else -> R.raw.treselefantes
                        }

                        mediaPlayer?.release()
                        mediaPlayer = MediaPlayer.create(mContext, soundResId).apply {
                            start()
                            setOnCompletionListener {
                                release()
                                mediaPlayer = null
                                background = when (background) {
                                    R.drawable.fondo_game2 -> R.drawable.fondo_game2_1
                                    R.drawable.fondo_game2_1 -> R.drawable.fondo_game2_2
                                    R.drawable.fondo_game2_2 -> R.drawable.fondo_game2_3
                                    else -> {
                                        showCompletionDialog = true
                                        R.drawable.fondo_game2_3
                                    }
                                }
                            }
                        }
                    }
            )
        }

        if (showCompletionDialog) {
            LevelCompletionDialog(
                onDismissRequest = { showCompletionDialog = false },
                onAdvanceClick = {
                    showCompletionDialog = false
                    playerInfoActivity2Completed.launch(Intent(this@GameLevel2Activity, PlayerInfoActivity2Completed::class.java))
                }
            )
        }
    }
}



