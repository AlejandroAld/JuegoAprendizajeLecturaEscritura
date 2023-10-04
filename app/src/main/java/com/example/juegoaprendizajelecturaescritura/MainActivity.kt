package com.example.juegoaprendizajelecturaescritura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.media.MediaPlayer
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    var playerName by remember { mutableStateOf("") }
    var currentScreen by remember { mutableStateOf(Screen.Welcome) }

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

        when (currentScreen) {
            Screen.Welcome -> {
                // Pantalla de bienvenida
                WelcomeScreen { currentScreen = Screen.GetName }
            }

            Screen.GetName -> {
                GetNameScreen(playerName) { name ->
                    playerName = name
                    currentScreen = Screen.PlayerInfo
                }
            }

            Screen.PlayerInfo -> {
                PlayerInfoScreen(playerName) { screen ->
                    when (screen) {
                        Screen.Level1 -> {
                            currentScreen = Screen.Level1
                        }
                        else -> {
                            // Me pide poner esto, si no lo pongo me marca error
                        }
                    }
                }
            }

            Screen.Level1 -> {
                GameLevel1 {
                    currentScreen = Screen.PlayerInfo
                }
            }
        }
    }
}



@Composable
fun WelcomeScreen(onPlayClick: () -> Unit) {
    val gameName = stringResource(id = R.string.game_name)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = gameName,
            fontSize = 35.sp,
            color = Color.Black,
            modifier = Modifier.padding(16.dp),
            style = TextStyle(
                letterSpacing = 2.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                shadow = Shadow(
                    color = Color.White,
                    offset = Offset(2f, 2f),
                    blurRadius = 12f
                )
            ),
            textAlign = TextAlign.Center
        )

        // Imagen en lugar del botón de texto "PLAY"
        Image(
            painter = painterResource(id = R.drawable.play_logo),
            contentDescription = "Botón PLAY",
            modifier = Modifier
                .size(200.dp)
                .clickable { onPlayClick() }
        )
    }
}



@Composable
fun GetNameScreen(playerName: String, onNameEntered: (String) -> Unit) {
    var name by remember { mutableStateOf(playerName) }
    val mContext = LocalContext.current
    val mediaPlayer = MediaPlayer.create(mContext, R.raw.ingresar_nombre)

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
            textStyle = LocalTextStyle.current.copy(fontSize =40.sp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .border(2.dp, Color.Green),
            singleLine = true,
            maxLines = 1,
            visualTransformation = VisualTransformation.None,
            interactionSource = remember { MutableInteractionSource() },
            cursorBrush = SolidColor(Color.Black),
            decorationBox = { innerTextField ->
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onNameEntered(name)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp).background(Color(0xFF8B4513))
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFB4513),
                contentColor = Color.White)
        ) {
            Text(text = "A Jugar", color = Color.White)
        }
    }
}

@Composable
fun PlayerInfoScreen(playerName: String?, onLevelSelected: (Screen) -> Unit) {
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
            Canvas(
                modifier = Modifier.fillMaxSize(),
                onDraw = {
                    val path = Path()
                    path.moveTo(0f, size.height)
                    path.lineTo(size.width / 4, size.height)
                    path.lineTo(size.width / 2, size.height / 4)
                    path.lineTo(size.width * 3 / 4, size.height)
                    path.lineTo(size.width, size.height)
                    drawPath(
                        path = path,
                        color = Color(0xFF8B4513), // Color café
                    )
                }
            )


            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .background(Color(0xFF8B4513))
                    .clickable {
                        onLevelSelected(Screen.Level1)
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
fun GameLevel1(onLevelCompleted: () -> Unit) {
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
                                onLevelCompleted()
                            }
                        }
                    )
                }
            }
        }
    }
}


enum class Screen {
    Welcome,
    GetName,
    PlayerInfo,
    Level1
    // Faltan agregar mas pantallas
}
