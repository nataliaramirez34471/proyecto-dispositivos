package com.example.proyecto_dispositivos.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyecto_dispositivos.ui.theme.ProyectodispositivosTheme

/** Título de la aplicación que se muestra en el login. */
private const val NOMBRE_APP = "Proyecto Dispositivos"

/**
 * Pantalla de inicio de sesión.
 *
 * Valida de forma local que los campos no estén vacíos.
 * Todavía no hay conexión con una base de datos.
 */
@Composable
fun LoginScreen(
    onInicioExitoso: () -> Unit,
    modifier: Modifier = Modifier
) {
    var usuario by rememberSaveable { mutableStateOf("") }
    var contrasena by rememberSaveable { mutableStateOf("") }
    var mensajeError by rememberSaveable { mutableStateOf("") }

    fun intentarIniciarSesion() {
        val error = validarCampos(usuario = usuario, contrasena = contrasena)
        mensajeError = error.orEmpty()
        if (error == null) {
            onInicioExitoso()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(modifier = Modifier.widthIn(max = 420.dp)) {
            Text(
                text = NOMBRE_APP,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Inicia sesión para continuar",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = usuario,
                onValueChange = {
                    usuario = it
                    if (mensajeError.isNotEmpty()) mensajeError = ""
                },
                label = { Text("Usuario o correo") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = contrasena,
                onValueChange = {
                    contrasena = it
                    if (mensajeError.isNotEmpty()) mensajeError = ""
                },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { intentarIniciarSesion() }),
                modifier = Modifier.fillMaxWidth()
            )

            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { intentarIniciarSesion() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(text = "Iniciar sesión")
            }
        }
    }
}

/**
 * Valida los campos del login.
 * Devuelve el mensaje de error o `null` si todo está correcto.
 */
private fun validarCampos(usuario: String, contrasena: String): String? {
    if (usuario.isBlank() || contrasena.isBlank()) {
        return "Debes completar todos los campos"
    }
    return null
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    ProyectodispositivosTheme {
        LoginScreen(onInicioExitoso = {})
    }
}
