package com.example.proyecto_dispositivos.data

import java.util.Calendar

/**
 * Modelo que representa una clase dentro del horario.
 */
data class ClaseHorario(
    val horaInicio: String,
    val horaFin: String,
    val materia: String
) {
    /** Rango horario completo, por ejemplo: "7:00 AM - 9:00 AM". */
    val horario: String
        get() = "$horaInicio - $horaFin"
}

/**
 * Modelo que representa un día de la semana con sus clases.
 */
data class DiaHorario(
    val dia: String,
    val clases: List<ClaseHorario>
)

/**
 * Datos de ejemplo del horario académico.
 * Cuando exista una fuente real de datos, solo hay que reemplazar esta lista.
 */
object HorarioEjemplo {

    val dias: List<DiaHorario> = listOf(
        DiaHorario(
            dia = "Lunes",
            clases = listOf(
                ClaseHorario(
                    horaInicio = "7:00 AM",
                    horaFin = "9:00 AM",
                    materia = "Programación para dispositivos móviles"
                ),
                ClaseHorario(
                    horaInicio = "9:00 AM",
                    horaFin = "11:00 AM",
                    materia = "Matemáticas"
                )
            )
        ),
        DiaHorario(
            dia = "Martes",
            clases = listOf(
                ClaseHorario(
                    horaInicio = "7:00 AM",
                    horaFin = "9:00 AM",
                    materia = "Bases de datos"
                ),
                ClaseHorario(
                    horaInicio = "9:00 AM",
                    horaFin = "11:00 AM",
                    materia = "Ingeniería de software"
                )
            )
        ),
        DiaHorario(
            dia = "Miércoles",
            clases = listOf(
                ClaseHorario(
                    horaInicio = "7:00 AM",
                    horaFin = "9:00 AM",
                    materia = "Programación"
                )
            )
        )
    )

    /** Devuelve las clases del día indicado, o una lista vacía si ese día no tiene clases. */
    fun clasesDe(dia: String): List<ClaseHorario> =
        dias.firstOrNull { it.dia == dia }?.clases.orEmpty()

    /** Nombre del día de la semana actual, por ejemplo: "Lunes". */
    fun diaDeHoy(): String = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
        Calendar.MONDAY -> "Lunes"
        Calendar.TUESDAY -> "Martes"
        Calendar.WEDNESDAY -> "Miércoles"
        Calendar.THURSDAY -> "Jueves"
        Calendar.FRIDAY -> "Viernes"
        Calendar.SATURDAY -> "Sábado"
        Calendar.SUNDAY -> "Domingo"
        else -> ""
    }
}
