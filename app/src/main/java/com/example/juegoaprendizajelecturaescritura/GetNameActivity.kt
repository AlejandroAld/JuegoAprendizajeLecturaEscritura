package com.example.juegoaprendizajelecturaescritura

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class GetNameActivity : ComponentActivity() {
    private val returnMain = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GetNameScreen(playerName = "", onNameEntered = { name ->
                // Navegar a la próxima actividad con un Intent
                val intent = Intent(this@GetNameActivity, PlayerInfoActivity::class.java)
                intent.putExtra("playerName", name)
                startActivity(intent)
            })
        }
    }

    @Composable
    fun GetNameScreen(playerName: String, onNameEntered: (String) -> Unit) {
        var name by remember { mutableStateOf(playerName) }
        val mContext = LocalContext.current
        val mediaPlayer = MediaPlayer.create(mContext, R.raw.ingresar_nombre)


        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.background_1_2),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start
            ){
                Image(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .size(98.dp)
                        .clickable {
                            returnMain.launch(Intent(this@GetNameActivity, MainActivity::class.java))
                        }
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Imagen que reproduce un sonido al hacer clic
                Image(
                    painter = painterResource(id = R.drawable.sound_icon),
                    contentDescription = "Sonido",
                    modifier = Modifier
                        .size(98.dp)
                        .clickable {
                            mediaPlayer.start() // Reproduce el sonido al hacer clic en la imagen
                        }
                )

                Spacer(modifier = Modifier.height(16.dp))

                BasicTextField(
                    value = name,
                    onValueChange = { newName -> name = newName },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onNameEntered(name)
                        }
                    ),
                    textStyle = LocalTextStyle.current.copy(fontSize = 40.sp),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .border(2.dp, Color.Green),
                    singleLine = true,
                    maxLines = 1,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = remember { MutableInteractionSource() },
                    cursorBrush = SolidColor(Color.Black)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onNameEntered(name)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(Color(0xFF8B4513))
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFB4513),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "A Jugar", color = Color.White)
                }
            }
        }
    }
}


