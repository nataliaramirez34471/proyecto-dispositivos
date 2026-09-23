package com.example.proyecto_dispositivos.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyecto_dispositivos.navigation.Pantalla
import com.example.proyecto_dispositivos.ui.theme.ProyectodispositivosTheme

/**
 * Espacio reservado para los módulos que aún no se implementan
 * (Tareas, Calendario, Calificaciones y Perfil).
 *
 * La navegación ya funciona: cuando otro integrante desarrolle el módulo,
 * solo hay que reemplazar este contenido en [com.example.proyecto_dispositivos.navigation.AppNavigation].
 *
 * @param onBack callback para volver a la pantalla principal.
 */
@Composable
fun ProximamenteScreen(
    pantalla: Pantalla,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        TextButton(onClick = onBack) {
            Text(text = "← Volver al inicio")
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = pantalla.titulo,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Este módulo estará disponible pronto.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Su desarrollo está a cargo de otro integrante del equipo.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProximamenteScreenPreview() {
    ProyectodispositivosTheme {
        ProximamenteScreen(pantalla = Pantalla.TAREAS, onBack = {})
    }
}
