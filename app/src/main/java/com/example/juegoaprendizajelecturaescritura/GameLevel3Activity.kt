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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class GameLevel3Activity : ComponentActivity() {
    private val playerInfoActivity3Completed = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameLevel3()
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
    fun GameLevel3() {
        var showDialog by remember { mutableStateOf(false) }
        val mContext = LocalContext.current
        var activatedCards by remember { mutableStateOf(0) } // Contador de tarjetas activadas
        val totalCards = 6 // Total de tarjetas

        val cat = MediaPlayer.create(mContext, R.raw.cat)
        val dog = MediaPlayer.create(mContext, R.raw.dog)
        val horse = MediaPlayer.create(mContext, R.raw.horse)
        val buffalo = MediaPlayer.create(mContext, R.raw.buffalo)
        val cock = MediaPlayer.create(mContext, R.raw.cock)
        val goat = MediaPlayer.create(mContext, R.raw.goat)
        fun handleCardClick(mediaPlayer: MediaPlayer) {
            mediaPlayer.start()
            activatedCards++
            if (activatedCards == totalCards) {
                showDialog = true
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.fondo_game_3),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(10.dp)
            ) {

                Row(modifier = Modifier.weight(1f)) {

                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(1f)
                            .clickable { handleCardClick(cat) }
                    ) {

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()) {

                            Image(
                                painter = painterResource(id = R.drawable.cat),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                        }

                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(1f)
                            .clickable { handleCardClick(dog) }
                    ) {

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()) {

                            Image(
                                painter = painterResource(id = R.drawable.dog),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                        }

                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.weight(1f)) {

                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(1f)
                            .clickable { handleCardClick(horse) }
                    ) {

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()) {

                            Image(
                                painter = painterResource(id = R.drawable.horse),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                        }

                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(1f)
                            .clickable { handleCardClick(buffalo) }
                    ) {

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()) {

                            Image(
                                painter = painterResource(id = R.drawable.buffalo),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                        }

                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.weight(1f)) {

                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(1f)
                            .clickable { handleCardClick(cock) }
                    ) {

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()) {

                            Image(
                                painter = painterResource(id = R.drawable.cock),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                        }

                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(1f)
                            .clickable { handleCardClick(goat) }
                    ) {

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()) {

                            Image(
                                painter = painterResource(id = R.drawable.goat),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                        }

                    }
                }
                // Muestra el diálogo cuando showDialog es true
                if (showDialog) {
                    LevelCompletionDialog(
                        onDismissRequest = { showDialog = false },
                        onAdvanceClick = {
                            showDialog = false
                            playerInfoActivity3Completed.launch(
                                Intent(this@GameLevel3Activity, PlayerInfoActivity3Completed::class.java)
                            )
                        }
                    )
                }
            }
        }
    }
}



