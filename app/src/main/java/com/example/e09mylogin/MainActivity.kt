package com.example.e09mylogin

//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e09mylogin.navigation.Navigation
import com.example.e09mylogin.ui.theme.E09MyLoginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            E09MyLoginTheme {
                //A surface container using the 'background' color from the theme

                //OJO !!!! HABILITAR INTERNET EN EL MANIFESTT

                Surface(
                    modifier = Modifier.fillMaxSize().padding(top = 46.dp),
                    //modifier = Modifier.fillmaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Navigation()
                    }
                }

            }
        }
    }
}
