package com.example.proyecto_dispositivos.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.proyecto_dispositivos.model.Tarea
import com.example.proyecto_dispositivos.ui.calendario.CalendarioScreen
import com.example.proyecto_dispositivos.ui.tareas.TareasScreen

/** Direcciones (rutas) de cada pantalla. */
object Rutas {
    const val TAREAS = "tareas"
    const val CALENDARIO = "calendario"
}

/** Pantallas que aparecen en la barra de abajo. */
data class Pantalla(
    val ruta: String,
    val titulo: String,
    val icono: ImageVector
)

val pantallas = listOf(
    Pantalla(Rutas.TAREAS, "Tareas", Icons.AutoMirrored.Filled.List),
    Pantalla(Rutas.CALENDARIO, "Calendario", Icons.Default.DateRange)
)

/** Ejemplos que aparecen al abrir la aplicación. */
val tareasDeEjemplo = listOf(
    Tarea(
        id = 1,
        nombre = "Exposición Android Studio",
        materia = "Programación para dispositivos móviles",
        fecha = "25/09/2026"
    ),
    Tarea(
        id = 2,
        nombre = "Consulta de bases de datos",
        materia = "Bases de datos",
        fecha = "28/09/2026"
    )
)

/**
 * Contenedor de la aplicación: tiene la lista de tareas en memoria
 * y la navegación entre la pantalla de Tareas y la de Calendario.
 *
 * Las dos pantallas comparten la misma lista, por eso una tarea agregada
 * o completada se refleja inmediatamente en el calendario.
 */
@Composable
fun AppNavigation() {
    // Lista de tareas "en memoria": se pierde al cerrar la aplicación.
    val tareas = remember { mutableStateListOf<Tarea>().apply { addAll(tareasDeEjemplo) } }

    // Siguiente id para las tareas nuevas.
    var siguienteId by remember { mutableIntStateOf(tareas.size + 1) }

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                pantallas.forEach { pantalla ->
                    NavigationBarItem(
                        selected = rutaActual == pantalla.ruta,
                        onClick = {
                            navController.navigate(pantalla.ruta) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = pantalla.icono,
                                contentDescription = pantalla.titulo
                            )
                        },
                        label = { Text(pantalla.titulo) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Rutas.TAREAS,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Rutas.TAREAS) {
                TareasScreen(
                    tareas = tareas,
                    onAgregar = { nombre, materia, fecha ->
                        tareas.add(
                            Tarea(
                                id = siguienteId,
                                nombre = nombre,
                                materia = materia,
                                fecha = fecha
                            )
                        )
                        siguienteId++
                    },
                    onCompletar = { tarea ->
                        val posicion = tareas.indexOfFirst { it.id == tarea.id }
                        if (posicion >= 0) {
                            // copy() crea una copia con el estado cambiado.
                            tareas[posicion] = tareas[posicion].copy(completada = !tarea.completada)
                        }
                    }
                )
            }

            composable(Rutas.CALENDARIO) {
                CalendarioScreen(tareas = tareas)
            }
        }
    }
}
