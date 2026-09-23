package com.example.proyecto_dispositivos.model

/**
 * Tarea académica del estudiante.
 *
 * Es una clase sencilla (data class) para que sea fácil de modificar.
 *
 * @param id Identificador numérico para poder diferenciar una tarea de otra.
 * @param nombre Nombre de la tarea, por ejemplo: "Exposición Android Studio".
 * @param materia Materia a la que pertenece, por ejemplo: "Bases de datos".
 * @param fecha Fecha de entrega en formato "dd/MM/aaaa", por ejemplo: "25/09/2026".
 * @param completada Indica si la tarea ya fue completada.
 */
data class Tarea(
    val id: Int,
    val nombre: String,
    val materia: String,
    val fecha: String,
    val completada: Boolean = false
)
