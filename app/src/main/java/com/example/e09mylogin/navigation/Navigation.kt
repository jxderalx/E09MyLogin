package com.example.e09mylogin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.e09mylogin.screens.home.Home
import com.example.e09mylogin.screens.login.LoginScreen
import com.example.e09mylogin.screens.splash.SplashScreen
import com.example.e09mylogin.screens.welcome.WelcomeScreen


@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.name
        ) {
        composable(Screens.SplashScreen.name){
            SplashScreen(navController = navController)
        }
        composable(Screens.WelcomeScreen.name){
            WelcomeScreen(navController = navController)
        }
        composable(Screens.LoginScreen.name){
            LoginScreen(navController = navController)
        }
        composable(Screens.HomeScreen.name){
            Home(navController = navController)
        }

    }
}