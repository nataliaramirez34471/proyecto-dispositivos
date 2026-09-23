package com.example.proyecto_dispositivos.ui.calendario

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.proyecto_dispositivos.model.Tarea
import com.example.proyecto_dispositivos.util.parsearFecha
import java.util.Calendar

private val MESES = listOf(
    "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
)

// Semana que empieza en lunes: L, M, M, J, V, S, D
private val DIAS_SEMANA = listOf("L", "M", "M", "J", "V", "S", "D")

/**
 * Pantalla del CALENDARIO académico.
 *
 * Muestra un mes con sus días. Los días que tienen tareas se marcan con color
 * y, al tocar un día, se listan las tareas de esa fecha.
 *
 * @param tareas Lista completa de tareas (misma lista que usa la pantalla de Tareas).
 */
@Composable
fun CalendarioScreen(
    tareas: List<Tarea>,
    modifier: Modifier = Modifier
) {
    val hoy = remember { Calendar.getInstance() }

    // Estado del calendario: mes (1-12), año y día seleccionado.
    var anio by remember { mutableStateOf(hoy.get(Calendar.YEAR)) }
    var mes by remember { mutableStateOf(hoy.get(Calendar.MONTH) + 1) }
    var diaSeleccionado by remember { mutableStateOf<Int?>(null) }

    // Tareas que caen dentro del mes que se está viendo (ordenadas por fecha).
    val tareasDelMes = tareas
        .filter { tarea ->
            val (_, mesTarea, anioTarea) = parsearFecha(tarea.fecha)
            mesTarea == mes && anioTarea == anio
        }
        .sortedBy { tarea ->
            val (diaTarea, mesTarea, anioTarea) = parsearFecha(tarea.fecha)
            anioTarea * 10000 + mesTarea * 100 + diaTarea
        }

    val diasConTarea = tareasDelMes
        .map { parsearFecha(it.fecha).first }
        .toSet()

    // Primer día del mes, para saber cuántos huecos dejar al principio.
    val primerDia = remember(mes, anio) {
        Calendar.getInstance().apply { set(anio, mes - 1, 1) }
    }
    val diasEnMes = primerDia.getActualMaximum(Calendar.DAY_OF_MONTH)
    // Calendar devuelve 1 = domingo. Queremos que la semana empiece en lunes (0).
    val huecoInicial = (primerDia.get(Calendar.DAY_OF_WEEK) + 5) % 7

    // Celdas del calendario: null = espacio vacío antes/después del mes.
    val celdas: List<Int?> =
        List(huecoInicial) { null } +
                (1..diasEnMes).toList() +
                List((7 - (huecoInicial + diasEnMes) % 7) % 7) { null }

    val mesNombre = MESES[mes - 1]

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(8.dp))

        // ---------- Encabezado: mes y año ----------
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                diaSeleccionado = null
                if (mes == 1) {
                    mes = 12
                    anio -= 1
                } else {
                    mes -= 1
                }
            }) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Mes anterior")
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$mesNombre $anio",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${tareasDelMes.size} tarea(s) este mes",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = {
                diaSeleccionado = null
                if (mes == 12) {
                    mes = 1
                    anio += 1
                } else {
                    mes += 1
                }
            }) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Mes siguiente")
            }
        }

        Spacer(Modifier.height(8.dp))

        // ---------- Días de la semana ----------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            DIAS_SEMANA.forEach { dia ->
                Text(
                    text = dia,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        Spacer(Modifier.height(4.dp))

        // ---------- Rejilla de días ----------
        celdas.chunked(7).forEach { fila ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                fila.forEach { dia ->
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        if (dia == null) {
                            Spacer(Modifier.size(44.dp))
                        } else {
                            CeldaDia(
                                dia = dia,
                                tieneTarea = diasConTarea.contains(dia),
                                seleccionado = dia == diaSeleccionado,
                                onClick = {
                                    diaSeleccionado = if (diaSeleccionado == dia) null else dia
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // ---------- Tareas del mes o del día seleccionado ----------
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = if (diaSeleccionado != null) {
                    "Tareas del día $diaSeleccionado de $mesNombre"
                } else {
                    "Tareas de $mesNombre $anio"
                },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = {
                if (diaSeleccionado != null) {
                    diaSeleccionado = null
                } else {
                    // Botón "Hoy": vuelve al mes actual.
                    diaSeleccionado = null
                    mes = hoy.get(Calendar.MONTH) + 1
                    anio = hoy.get(Calendar.YEAR)
                }
            }) {
                Text(if (diaSeleccionado != null) "Ver mes" else "Hoy")
            }
        }

        Spacer(Modifier.height(8.dp))

        val tareasAMostrar = if (diaSeleccionado != null) {
            tareasDelMes.filter { parsearFecha(it.fecha).first == diaSeleccionado }
        } else {
            tareasDelMes
        }

        when {
            tareas.isEmpty() -> Text(
                text = "Aún no hay tareas. Agrega una desde la pantalla Tareas.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            tareasAMostrar.isEmpty() && diaSeleccionado != null -> Text(
                text = "No hay tareas el día $diaSeleccionado de $mesNombre.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            tareasAMostrar.isEmpty() -> Text(
                text = "Este mes no tiene tareas.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            else -> tareasAMostrar.forEach { tarea ->
                TarjetaCalendario(tarea = tarea)
                Spacer(Modifier.height(8.dp))
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}

/** Cuadrito de un día dentro del calendario. */
@Composable
private fun CeldaDia(
    dia: Int,
    tieneTarea: Boolean,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    val fondo = when {
        seleccionado -> MaterialTheme.colorScheme.primary
        tieneTarea -> MaterialTheme.colorScheme.secondaryContainer
        else -> Color.Transparent
    }
    val texto = when {
        seleccionado -> MaterialTheme.colorScheme.onPrimary
        tieneTarea -> MaterialTheme.colorScheme.onSecondaryContainer
        else -> MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(fondo)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dia.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (tieneTarea || seleccionado) FontWeight.Bold else FontWeight.Normal,
            color = texto
        )
    }
}

/** Fila con el detalle de una tarea dentro del calendario. */
@Composable
private fun TarjetaCalendario(tarea: Tarea) {
    val (dia, mes, _) = parsearFecha(tarea.fecha)
    val mesCorto = if (mes in 1..12) MESES[mes - 1].take(3).uppercase() else "---"

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Día y mes de la entrega.
            Column(
                modifier = Modifier.width(56.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (dia in 1..31) dia.toString() else "--",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = mesCorto,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tarea.nombre,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Materia: ${tarea.materia}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (tarea.completada) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "Completada",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Text(
                            text = "Pendiente",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
