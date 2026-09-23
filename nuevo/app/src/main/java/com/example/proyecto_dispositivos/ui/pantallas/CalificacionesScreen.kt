package com.example.proyecto_dispositivos.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyecto_dispositivos.data.Materia
import com.example.proyecto_dispositivos.data.formatearNota
import com.example.proyecto_dispositivos.data.materiasEjemplo
import com.example.proyecto_dispositivos.data.promedioGeneral
import com.example.proyecto_dispositivos.ui.theme.ProyectodispositivosTheme

private val ColorAprobada = Color(0xFF2E7D32)
private val ColorBaja = Color(0xFFC62828)

/**
 * Pantalla de Calificaciones del estudiante.
 * Muestra las materias, las notas de cada corte y los promedios.
 */
@Composable
fun CalificacionesScreen(
    materias: List<Materia> = materiasEjemplo,
    modifier: Modifier = Modifier
) {
    val promedioGeneral = materias.promedioGeneral()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
    ) {
        item {
            Text(
                text = "Calificaciones",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Promedio general",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = promedioGeneral.formatearNota(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        items(materias) { materia ->
            TarjetaMateria(materia = materia)
        }
    }
}

@Composable
private fun TarjetaMateria(materia: Materia) {
    val colorPromedio = if (materia.aprobada) ColorAprobada else ColorBaja

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = materia.nombre,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Notas de cada corte
            materia.cortes.forEachIndexed { indice, nota ->
                FilaCorte(
                    nombreCorte = "Corte ${indice + 1}",
                    nota = nota
                )
                if (indice < materia.cortes.lastIndex) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Promedio de la materia
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Promedio",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = materia.promedio.formatearNota(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorPromedio
                )
            }
        }
    }
}

@Composable
private fun FilaCorte(
    nombreCorte: String,
    nota: Double,
    modifier: Modifier = Modifier
) {
    val colorNota = if (nota >= Materia.NOTA_MINIMA) ColorAprobada else ColorBaja

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Punto verde si la nota está aprobada, rojo si es baja.
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(color = colorNota, shape = CircleShape)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = nombreCorte,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Text(
            text = nota.formatearNota(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = colorNota
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CalificacionesScreenPreview() {
    ProyectodispositivosTheme {
        CalificacionesScreen()
    }
}
