package com.example.juegoaprendizajelecturaescritura

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class PlayerInfoActivity2Completed : ComponentActivity() {
    private val getGameLevel1 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
    }
    private val getGameLevel2 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
    }
    private val getGameLevel3 = registerForActivityResult(
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
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fondo_niveles),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = R.drawable.nivel_completado),
                    contentDescription = "",
                    modifier = Modifier
                        // Apply padding only from de bottom
                        .padding(bottom = 40.dp)
                        .size(150.dp)
                        .align(Alignment.BottomStart)
                        .clickable {
                            // Navega a GetNameActivity
                            getGameLevel1.launch(Intent(this@PlayerInfoActivity2Completed, GameLevel1Activity::class.java))
                        }
                )

                Image(
                    painter = painterResource(id = R.drawable.nivel_completado),
                    contentDescription = "",
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.Center)
                        .clickable {
                            // Navega a GetNameActivity
                            getGameLevel2.launch(Intent(this@PlayerInfoActivity2Completed, GameLevel2Activity::class.java))
                        }
                )

                Image(
                    painter = painterResource(id = R.drawable.nivel),
                    contentDescription = "",
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.TopEnd)
                        .clickable {
                            // Navega a GetNameActivity
                            getGameLevel3.launch(Intent(this@PlayerInfoActivity2Completed, GameLevel3Activity::class.java))
                        }
                )
            }
        }
    }
}


