package com.example.proyecto_dispositivos.data

import java.util.Locale

/**
 * Representa una materia del estudiante junto con sus notas por corte.
 */
data class Materia(
    val nombre: String,
    val cortes: List<Double>
) {
    /** Promedio de la materia, calculado con Kotlin a partir de los cortes. */
    val promedio: Double
        get() = if (cortes.isEmpty()) 0.0 else cortes.average()

    /** Indicación simple: aprobada si el promedio es mayor o igual a 3.0. */
    val aprobada: Boolean
        get() = promedio >= NOTA_MINIMA

    companion object {
        const val NOTA_MINIMA = 3.0
    }
}

/** Formatea una nota con un solo decimal (ej. 4.23 -> "4.2"). */
fun Double.formatearNota(): String = "%.1f".format(Locale.US, this)

/** Datos de ejemplo mientras el estudiante no tiene información real. */
val materiasEjemplo: List<Materia> = listOf(
    Materia(
        nombre = "Programación para Dispositivos Móviles",
        cortes = listOf(4.2, 4.0, 4.5)
    ),
    Materia(
        nombre = "Bases de Datos",
        cortes = listOf(3.8, 4.1, 4.3)
    ),
    Materia(
        nombre = "Matemáticas",
        cortes = listOf(3.5, 4.0, 3.9)
    )
)

/** Promedio general del estudiante (promedio de los promedios de cada materia). */
fun List<Materia>.promedioGeneral(): Double =
    if (isEmpty()) 0.0 else map { it.promedio }.average()
