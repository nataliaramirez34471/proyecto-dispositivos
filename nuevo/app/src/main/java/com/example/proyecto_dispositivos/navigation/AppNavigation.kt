package com.example.proyecto_dispositivos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.proyecto_dispositivos.ui.login.LoginScreen
import com.example.proyecto_dispositivos.ui.perfil.PerfilScreen

/** Rutas (pantallas) de la aplicación. */
object Rutas {
    const val LOGIN = "login"
    const val PRINCIPAL = "principal"
}

/**
 * Navegación de la aplicación.
 *
 * Se guarda la ruta actual en un estado: cuando el usuario inicia sesión
 * se cambia a la pantalla principal y cuando cierra sesión se vuelve al login.
 * [rememberSaveable] conserva la pantalla actual si el sistema recrea la Activity
 * (por ejemplo, al rotar el dispositivo).
 */
@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    var pantallaActual by rememberSaveable { mutableStateOf(Rutas.LOGIN) }

    when (pantallaActual) {
        Rutas.LOGIN -> LoginScreen(
            modifier = modifier,
            onInicioExitoso = { pantallaActual = Rutas.PRINCIPAL }
        )

        else -> PerfilScreen(
            modifier = modifier,
            onCerrarSesion = { pantallaActual = Rutas.LOGIN }
        )
    }
}
