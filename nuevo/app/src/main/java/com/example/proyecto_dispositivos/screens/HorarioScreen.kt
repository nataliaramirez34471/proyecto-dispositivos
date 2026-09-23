package com.example.proyecto_dispositivos.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyecto_dispositivos.data.ClaseHorario
import com.example.proyecto_dispositivos.data.DiaHorario
import com.example.proyecto_dispositivos.data.HorarioEjemplo
import com.example.proyecto_dispositivos.ui.theme.ProyectodispositivosTheme

/**
 * Pantalla de Horario: muestra las clases de la semana
 * organizadas por día en tarjetas.
 *
 * @param onBack callback para volver a la pantalla principal.
 */
@Composable
fun HorarioScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextButton(onClick = onBack) {
            Text(text = "← Volver al inicio")
        }

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "Horario",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Horario académico de la semana",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        HorarioEjemplo.dias.forEach { dia ->
            TarjetaDia(dia = dia)
        }
    }
}

/**
 * Tarjeta con las clases de un día de la semana.
 */
@Composable
private fun TarjetaDia(
    dia: DiaHorario,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = dia.dia,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            if (dia.clases.isEmpty()) {
                Text(
                    text = "Sin clases",
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                dia.clases.forEachIndexed { index, clase ->
                    if (index > 0) {
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    }
                    ItemClase(clase = clase)
                }
            }
        }
    }
}

/**
 * Una fila del horario: materia y rango horario.
 */
@Composable
private fun ItemClase(
    clase: ClaseHorario,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = clase.materia,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = clase.horario,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HorarioScreenPreview() {
    ProyectodispositivosTheme {
        HorarioScreen(onBack = {})
    }
}
