package com.example.proyecto_dispositivos.ui.tareas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.proyecto_dispositivos.util.crearFecha

/**
 * Ventana con el formulario para agregar una tarea nueva.
 *
 * @param onCerrar Se ejecuta al cancelar o al cerrar la ventana.
 * @param onGuardar Devuelve (nombre, materia, fecha) cuando el formulario es válido.
 */
@Composable
fun AgregarTareaDialog(
    onCerrar: () -> Unit,
    onGuardar: (nombre: String, materia: String, fecha: String) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var materia by remember { mutableStateOf("") }
    var dia by remember { mutableStateOf("") }
    var mes by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }

    // Guarda los mensajes de error de cada campo.
    var errores by remember { mutableStateOf(emptyMap<String, String>()) }

    fun guardar() {
        val nuevosErrores = mutableMapOf<String, String>()

        if (nombre.isBlank()) nuevosErrores["nombre"] = "Escribe el nombre de la tarea"
        if (materia.isBlank()) nuevosErrores["materia"] = "Escribe la materia"

        val d = dia.toIntOrNull()
        val m = mes.toIntOrNull()
        val a = anio.toIntOrNull()
        val fechaValida = d != null && d in 1..31 &&
                m != null && m in 1..12 &&
                a != null && a in 1000..9999

        if (!fechaValida) {
            nuevosErrores["fecha"] = "Fecha no válida (ejemplo: 25 / 09 / 2026)"
        }

        errores = nuevosErrores

        if (nuevosErrores.isEmpty() && d != null && m != null && a != null) {
            onGuardar(
                nombre.trim(),
                materia.trim(),
                crearFecha(d, m, a)
            )
        }
    }

    AlertDialog(
        onDismissRequest = onCerrar,
        title = { Text("Nueva tarea") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        errores = errores - "nombre"
                    },
                    label = { Text("Nombre de la tarea") },
                    singleLine = true,
                    isError = errores.containsKey("nombre"),
                    modifier = Modifier.fillMaxWidth()
                )
                TextoDeError(errores["nombre"])

                OutlinedTextField(
                    value = materia,
                    onValueChange = {
                        materia = it
                        errores = errores - "materia"
                    },
                    label = { Text("Materia") },
                    singleLine = true,
                    isError = errores.containsKey("materia"),
                    modifier = Modifier.fillMaxWidth()
                )
                TextoDeError(errores["materia"])

                Text(
                    text = "Fecha de entrega",
                    style = MaterialTheme.typography.labelLarge
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CampoFecha(
                        valor = dia,
                        etiqueta = "Día",
                        onChange = {
                            dia = it
                            errores = errores - "fecha"
                        },
                        modifier = Modifier.weight(1f)
                    )
                    CampoFecha(
                        valor = mes,
                        etiqueta = "Mes",
                        onChange = {
                            mes = it
                            errores = errores - "fecha"
                        },
                        modifier = Modifier.weight(1f)
                    )
                    CampoFecha(
                        valor = anio,
                        etiqueta = "Año",
                        onChange = {
                            anio = it
                            errores = errores - "fecha"
                        },
                        modifier = Modifier.weight(1.4f)
                    )
                }
                TextoDeError(errores["fecha"])
            }
        },
        confirmButton = {
            Button(onClick = { guardar() }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onCerrar) {
                Text("Cancelar")
            }
        }
    )
}

/** Campo pequeño para escribir día, mes o año. */
@Composable
private fun CampoFecha(
    valor: String,
    etiqueta: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onChange,
        label = { Text(etiqueta) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun TextoDeError(error: String?) {
    if (error != null) {
        Text(
            text = error,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error
        )
    }
}
