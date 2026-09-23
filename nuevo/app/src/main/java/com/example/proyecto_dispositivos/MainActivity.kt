package com.example.proyecto_dispositivos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.proyecto_dispositivos.navigation.AppNavigation
import com.example.proyecto_dispositivos.ui.theme.ProyectodispositivosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectodispositivosTheme {
                AppNavigation()
            }
        }
    }
}
