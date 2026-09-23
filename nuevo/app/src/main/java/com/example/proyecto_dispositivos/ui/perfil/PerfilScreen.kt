package com.example.proyecto_dispositivos.ui.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyecto_dispositivos.model.Estudiante
import com.example.proyecto_dispositivos.ui.theme.ProyectodispositivosTheme

/**
 * Pantalla principal con el perfil del estudiante.
 *
 * Muestra datos de ejemplo. Para usar datos reales basta con pasar
 * otro objeto [Estudiante] desde la navegación o desde una base de datos.
 */
@Composable
fun PerfilScreen(
    onCerrarSesion: () -> Unit,
    modifier: Modifier = Modifier,
    estudiante: Estudiante = Estudiante.ejemplo
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.widthIn(max = 560.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Mi perfil",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Avatar(nombre = estudiante.nombre)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = estudiante.nombre,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Text(
                text = estudiante.correo,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column {
                    DatoPerfil(etiqueta = "Código estudiantil", valor = estudiante.codigoEstudiantil)
                    HorizontalDivider()
                    DatoPerfil(etiqueta = "Programa académico", valor = estudiante.programa)
                    HorizontalDivider()
                    DatoPerfil(etiqueta = "Semestre", valor = estudiante.semestre)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onCerrarSesion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text(text = "Cerrar sesión")
            }
        }
    }
}

/** Círculo con las iniciales del estudiante. */
@Composable
private fun Avatar(nombre: String, modifier: Modifier = Modifier) {
    val iniciales = nombre
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .map { it.first().uppercase() }
        .joinToString("")

    Box(
        modifier = modifier
            .size(96.dp)
            .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = iniciales,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

/** Fila con una etiqueta y su valor dentro de la tarjeta de datos. */
@Composable
private fun DatoPerfil(etiqueta: String, valor: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PerfilScreenPreview() {
    ProyectodispositivosTheme {
        PerfilScreen(onCerrarSesion = {})
    }
}
