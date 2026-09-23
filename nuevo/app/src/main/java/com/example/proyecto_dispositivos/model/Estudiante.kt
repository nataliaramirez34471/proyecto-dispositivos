package com.example.proyecto_dispositivos.model

/**
 * Información del estudiante que se muestra en la pantalla de perfil.
 *
 * Por ahora se usa información de ejemplo. Cuando el proyecto tenga
 * una base de datos o un servicio, solo será necesario construir este
 * objeto con los datos reales (por ejemplo, desde Firestore o una API).
 */
data class Estudiante(
    val nombre: String,
    val correo: String,
    val codigoEstudiantil: String,
    val programa: String,
    val semestre: String
) {
    companion object {
        /** Datos de ejemplo mientras no hay conexión con una base de datos. */
        val ejemplo = Estudiante(
            nombre = "Ana García López",
            correo = "ana.garcia@estudiantes.edu",
            codigoEstudiantil = "2025123456",
            programa = "Ingeniería de Sistemas",
            semestre = "5"
        )
    }
}
