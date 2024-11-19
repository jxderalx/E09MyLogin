package com.example.e09mylogin.screens.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e09mylogin.navigation.Screens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController : NavController) {

    //ANIMACION
    val sweepAngle = remember { androidx.compose.animation.core.Animatable(0f) }
    val scale = remember { androidx.compose.animation.core.Animatable(1f) }


    LaunchedEffect(key1 = true) {
        // Animación de llenado
        sweepAngle.animateTo(
            targetValue = 360f,
            animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
        )

        //espero un momento antes de iniciar la expansión
        delay(20)

        // Animación de expansión
        scale.animateTo(
            targetValue = 10f, // Aumenta este valor para hacer que los bordes salgan más rápido de la pantalla
            animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
        )

        delay(10)

        //ir a la siguiente pantalla
        navController.navigate(Screens.WelcomeScreen.name)
        //si ya está logueado el usuario no necesita autenticarse de nuevo
        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
            navController.navigate(Screens.WelcomeScreen.name)
        } else {
            navController.navigate(Screens.HomeScreen.name) {
                //al pulsar boton atraas vuelve a splash, para evitar esto
                //sacamos el splash de la lista de pantallas recorridas
                popUpTo(Screens.SplashScreen.name) {
                    inclusive = true
                }
            }
        }
    }

    val circleColor = Color(0xFF00FF00)

    // DIBUJO DE LA BARRA CIRCULAR CON ANIMACIÓN DE LLENADO Y EXPANSIÓN
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(30.dp)
            .scale(scale.value) //animacion de zoom
    ) {
        Canvas(modifier = Modifier.size(150.dp)) {
            drawArc(
                color = circleColor,
                startAngle = 0f,
                sweepAngle = sweepAngle.value,
                useCenter = false,
                style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
            )
        }

    }
}