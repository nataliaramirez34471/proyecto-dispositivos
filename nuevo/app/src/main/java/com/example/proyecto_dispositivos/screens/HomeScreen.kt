package com.example.proyecto_dispositivos.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyecto_dispositivos.R
import com.example.proyecto_dispositivos.data.ClaseHorario
import com.example.proyecto_dispositivos.data.HorarioEjemplo
import com.example.proyecto_dispositivos.navigation.Pantalla
import com.example.proyecto_dispositivos.ui.theme.ProyectodispositivosTheme

/** Nombre que se muestra en el saludo. Cambiar cuando exista Login con sesión real. */
private const val NOMBRE_ESTUDIANTE = "Estudiante"

/** Módulos accesibles desde la pantalla principal. */
private val accesosPrincipales = listOf(
    Pantalla.HORARIO,
    Pantalla.TAREAS,
    Pantalla.CALENDARIO,
    Pantalla.CALIFICACIONES,
    Pantalla.PERFIL
)

/**
 * Pantalla principal / Home del estudiante.
 *
 * @param onNavigate callback para abrir otra pantalla de la aplicación.
 */
@Composable
fun HomeScreen(
    onNavigate: (Pantalla) -> Unit,
    modifier: Modifier = Modifier
) {
    val diaActual = HorarioEjemplo.diaDeHoy()
    val clasesDeHoy = HorarioEjemplo.clasesDe(diaActual)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Encabezado: nombre de la aplicación y saludo.
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Hola, $NOMBRE_ESTUDIANTE 👋",
            style = MaterialTheme.typography.titleLarge
        )

        // Resumen del día.
        TarjetaResumenDelDia(
            diaActual = diaActual,
            clasesDeHoy = clasesDeHoy
        )

        // Accesos a los módulos (dos tarjetas por fila).
        Text(
            text = "Módulos",
            style = MaterialTheme.typography.titleMedium
        )
        accesosPrincipales.chunked(2).forEach { fila ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                fila.forEach { pantalla ->
                    TarjetaAcceso(
                        pantalla = pantalla,
                        onClick = { onNavigate(pantalla) },
                        modifier = Modifier.weight(1f)
                    )
                }
                // Mantiene alineada la última fila si tiene una sola tarjeta.
                if (fila.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

/**
 * Tarjeta con el resumen del día: día actual y primeras clases.
 */
@Composable
private fun TarjetaResumenDelDia(
    diaActual: String,
    clasesDeHoy: List<ClaseHorario>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Resumen del día",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = if (diaActual.isEmpty()) "Hoy" else "Hoy es $diaActual",
                style = MaterialTheme.typography.bodyMedium
            )
            if (clasesDeHoy.isEmpty()) {
                Text(
                    text = "No tienes clases programadas.",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                val cantidad = clasesDeHoy.size
                Text(
                    text = if (cantidad == 1) "1 clase programada" else "$cantidad clases programadas",
                    style = MaterialTheme.typography.bodyLarge
                )
                val primeraClase = clasesDeHoy.first()
                Text(
                    text = "Primera clase: ${primeraClase.horario} · ${primeraClase.materia}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Tarjeta de acceso a un módulo de la aplicación.
 */
@Composable
private fun TarjetaAcceso(
    pantalla: Pantalla,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = pantalla.titulo,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = pantalla.descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    ProyectodispositivosTheme {
        HomeScreen(onNavigate = {})
    }
}
