package com.example.e09mylogin.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e09mylogin.R
import com.example.e09mylogin.navigation.Screens

@Composable
fun WelcomeScreen(navController: NavController) {
    Surface(modifier = Modifier
        .fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2C2C2C), // Gris oscuro
                            Color(0xFF1C1C1C), // Gris casi negro
                            Color(0xFF000000)  // Negro
                        )
                    )
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen de Spotify
            Image(
                painter = painterResource(id = R.drawable.ic_spotify),
                contentDescription = "Spotify Logo",
                modifier = Modifier
                    .size(100.dp) // Ajusta el tamaño de la imagen según sea necesario
                    .padding(bottom = 20.dp) // Espacio entre la imagen y el texto
            )

            // Texto similar al de LoginScreen
            Text(
                text = "Millones de canciones.\nGratis en Spotify.",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp, // Ajusta el espacio entre las líneas
                modifier = Modifier.padding(bottom = 30.dp), // Ajusta la separación de las cajas de login
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Botón para navegar al LoginScreen
            Button(
                onClick = {
                    // Después de mostrar la pantalla de bienvenida, navegar a la pantalla de Login
                    navController.navigate(Screens.LoginScreen.name) {
                        popUpTo(Screens.SplashScreen.name) { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1DB954)  // Aquí aplicas el color hexadecimal
                )
            ) {
                Text(text = "Comencemos")
            }
        }
    }
}


@Preview
@Composable
fun PreviewWelcomeScreen() {
    WelcomeScreen(navController = rememberNavController())
}
