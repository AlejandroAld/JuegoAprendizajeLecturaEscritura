package com.example.juegoaprendizajelecturaescritura

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

class PlayerInfoActivity : ComponentActivity() {
    private val getGameLevel1 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlayerInfoScreen(playerName = intent.getStringExtra("playerName"))
        }
    }

    @Composable
    fun PlayerInfoScreen(playerName: String?) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color.White)
            ) {
                Text(
                    text = "Bienvenido $playerName",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(8.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Green)
            ) {

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .background(Color(0xFF8B4513))
                        .clickable {
                            getGameLevel1.launch(Intent(this@PlayerInfoActivity, GameLevel1Activity::class.java))
                        }
                ) {
                    Text(text = "Nivel 1", color = Color.White)
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 32.dp, vertical = 32.dp)
                        .background(Color(0xFF8B4513))
                        .clickable {
                            // Acción al hacer clic en el botón del nivel 2
                        }
                ) {
                    Text(text = "Nivel 2", color = Color.White)
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .background(Color(0xFF8B4513))
                        .clickable {
                            // Acción al hacer clic en el botón del nivel 3
                        }
                ) {
                    Text(text = "Nivel 3", color = Color.White)
                }
            }
        }
    }
}


