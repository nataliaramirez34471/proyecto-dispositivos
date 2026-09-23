package com.example.proyecto_dispositivos.util

/**
 * Funciones simples para trabajar con fechas en formato "dd/MM/aaaa".
 *
 * Se usa texto en lugar de java.time porque el proyecto tiene minSdk 24
 * (java.time necesita Android 8 en adelante).
 */

/** Crea una fecha con el formato "dd/MM/aaaa". Ejemplo: crearFecha(5, 9, 2026) -> "05/09/2026" */
fun crearFecha(dia: Int, mes: Int, anio: Int): String {
    val diaTexto = if (dia < 10) "0$dia" else "$dia"
    val mesTexto = if (mes < 10) "0$mes" else "$mes"
    return "$diaTexto/$mesTexto/$anio"
}

/**
 * Devuelve la fecha separada en (día, mes, año).
 * Ejemplo: parsearFecha("25/09/2026") -> Triple(25, 9, 2026)
 * Si la fecha no es válida devuelve Triple(0, 0, 0).
 */
fun parsearFecha(fecha: String): Triple<Int, Int, Int> {
    val partes = fecha.split("/")
    if (partes.size != 3) return Triple(0, 0, 0)

    val dia = partes[0].toIntOrNull() ?: 0
    val mes = partes[1].toIntOrNull() ?: 0
    val anio = partes[2].toIntOrNull() ?: 0

    if (dia !in 1..31 || mes !in 1..12 || anio < 1000) return Triple(0, 0, 0)

    return Triple(dia, mes, anio)
}
