package com.example.proyecto_dispositivos.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.proyecto_dispositivos.screens.HorarioScreen
import com.example.proyecto_dispositivos.screens.HomeScreen
import com.example.proyecto_dispositivos.screens.ProximamenteScreen

/**
 * Navegación principal de la aplicación.
 *
 * Todas las pantallas se definen en un solo lugar ([Pantalla]) para que
 * cualquier integrante pueda sumar módulos sin duplicar navegación.
 */
@Composable
fun AppNavigation() {
    var pantallaActual by rememberSaveable { mutableStateOf(Pantalla.HOME) }

    // Botón "atrás" del sistema: desde cualquier pantalla vuelve al inicio.
    BackHandler(enabled = pantallaActual != Pantalla.HOME) {
        pantallaActual = Pantalla.HOME
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        when (pantallaActual) {
            Pantalla.HOME -> HomeScreen(
                onNavigate = { pantallaActual = it },
                modifier = Modifier.padding(innerPadding)
            )

            Pantalla.HORARIO -> HorarioScreen(
                onBack = { pantallaActual = Pantalla.HOME },
                modifier = Modifier.padding(innerPadding)
            )

            // Espacio reservado para los módulos que desarrollarán otros integrantes.
            else -> ProximamenteScreen(
                pantalla = pantallaActual,
                onBack = { pantallaActual = Pantalla.HOME },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
