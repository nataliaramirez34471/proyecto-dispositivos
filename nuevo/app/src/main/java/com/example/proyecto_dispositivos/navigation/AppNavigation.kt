package com.example.proyecto_dispositivos.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.proyecto_dispositivos.Greeting
import com.example.proyecto_dispositivos.ui.pantallas.CalificacionesScreen

/** Pantallas de la aplicación. */
enum class Pantalla(val titulo: String) {
    INICIO("Inicio"),
    CALIFICACIONES("Calificaciones")
}

/**
 * Navegación principal de la aplicación.
 * Usa una fila de pestañas para cambiar entre las pantallas existentes.
 */
@Composable
fun AppNavigation() {
    var pantallaActual by remember { mutableStateOf(Pantalla.INICIO) }

    Scaffold(
        topBar = {
            PrimaryTabRow(selectedTabIndex = pantallaActual.ordinal) {
                Pantalla.entries.forEach { pantalla ->
                    Tab(
                        selected = pantallaActual == pantalla,
                        onClick = { pantallaActual = pantalla },
                        text = { Text(pantalla.titulo) }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (pantallaActual) {
            Pantalla.INICIO -> Greeting(
                name = "Android",
                modifier = Modifier.padding(innerPadding)
            )

            Pantalla.CALIFICACIONES -> CalificacionesScreen(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
