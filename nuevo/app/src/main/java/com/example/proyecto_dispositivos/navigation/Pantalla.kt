package com.example.proyecto_dispositivos.navigation

/**
 * Destinos disponibles en la aplicación.
 *
 * Para agregar una nueva pantalla basta con añadir un valor aquí
 * y tratarlo en [AppNavigation].
 */
enum class Pantalla(val titulo: String, val descripcion: String) {
    HOME("Inicio", "Resumen de tu día"),
    HORARIO("Horario", "Tus clases de la semana"),
    TAREAS("Tareas", "Pendientes y entregas"),
    CALENDARIO("Calendario", "Fechas importantes"),
    CALIFICACIONES("Calificaciones", "Tus notas del semestre"),
    PERFIL("Perfil", "Información del estudiante")
}
