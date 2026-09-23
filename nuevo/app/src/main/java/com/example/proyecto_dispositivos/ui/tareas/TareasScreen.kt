package com.example.proyecto_dispositivos.ui.tareas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.proyecto_dispositivos.model.Tarea

/**
 * Pantalla principal de TAREAS.
 *
 * Muestra la lista de tareas, permite agregar una nueva y marcarla como completada.
 * Los datos se guardan en memoria (no hay base de datos todavía).
 *
 * @param tareas Lista de tareas que se quieren mostrar.
 * @param onAgregar Se ejecuta cuando el usuario guarda una tarea nueva.
 * @param onCompletar Se ejecuta cuando el usuario marca/desmarca una tarea.
 */
@Composable
fun TareasScreen(
    tareas: List<Tarea>,
    onAgregar: (nombre: String, materia: String, fecha: String) -> Unit,
    onCompletar: (Tarea) -> Unit,
    modifier: Modifier = Modifier
) {
    // Controla si se muestra o no el formulario para agregar una tarea.
    var mostrarFormulario by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { mostrarFormulario = true },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Agregar tarea") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Tareas",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = textoResumen(tareas),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(16.dp))

            if (tareas.isEmpty()) {
                EstadoVacio()
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(tareas, key = { it.id }) { tarea ->
                        TarjetaTarea(tarea = tarea, onCompletar = onCompletar)
                    }
                    item { Spacer(Modifier.height(72.dp)) }
                }
            }
        }
    }

    // Formulario para crear una tarea nueva.
    if (mostrarFormulario) {
        AgregarTareaDialog(
            onCerrar = { mostrarFormulario = false },
            onGuardar = { nombre, materia, fecha ->
                onAgregar(nombre, materia, fecha)
                mostrarFormulario = false
            }
        )
    }
}

private fun textoResumen(tareas: List<Tarea>): String {
    if (tareas.isEmpty()) return "Aún no hay tareas"
    val completadas = tareas.count { it.completada }
    return "${tareas.size} tarea(s) · $completadas completada(s)"
}

/** Tarjeta de una tarea: nombre, materia, fecha y estado. */
@Composable
private fun TarjetaTarea(
    tarea: Tarea,
    onCompletar: (Tarea) -> Unit
) {
    val completada = tarea.completada

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (completada) {
                MaterialTheme.colorScheme.surfaceVariant
            } else {
                MaterialTheme.colorScheme.surfaceContainerLow
            }
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tarea.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = if (completada) TextDecoration.LineThrough else TextDecoration.None
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Materia: ${tarea.materia}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "Entrega: ${tarea.fecha}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(Modifier.height(8.dp))
                if (completada) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "Completada",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    Text(
                        text = "Pendiente",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Checkbox(
                    checked = completada,
                    onCheckedChange = { onCompletar(tarea) }
                )
                Text(
                    text = if (completada) "Hecha" else "Marcar",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/** Mensaje que se muestra cuando todavía no hay tareas. */
@Composable
private fun EstadoVacio() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "No hay tareas todavía",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Toca en \"Agregar tarea\" para crear la primera.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
