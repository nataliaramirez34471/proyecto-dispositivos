package com.example.proyecto_dispositivos

import com.example.proyecto_dispositivos.data.Materia
import com.example.proyecto_dispositivos.data.formatearNota
import com.example.proyecto_dispositivos.data.materiasEjemplo
import com.example.proyecto_dispositivos.data.promedioGeneral
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pruebas del cálculo de promedios del módulo de calificaciones.
 */
class CalificacionesTest {

    @Test
    fun promedio_de_materia_se_calcula_correctamente() {
        val materia = Materia(
            nombre = "Programación para Dispositivos Móviles",
            cortes = listOf(4.2, 4.0, 4.5)
        )

        assertEquals(4.2333, materia.promedio, 0.001)
    }

    @Test
    fun promedio_general_se_calcula_correctamente() {
        // Promedios: 4.2333, 4.0667 y 3.8 -> general = 4.0333
        val general = materiasEjemplo.promedioGeneral()

        assertEquals(4.0333, general, 0.001)
    }

    @Test
    fun lista_vacia_no_produce_error() {
        val materias = listOf<Materia>()

        assertEquals(0.0, materias.promedioGeneral(), 0.0)
        assertEquals(0.0, Materia("Vacía", emptyList()).promedio, 0.0)
    }

    @Test
    fun nota_aprobada_y_baja_se_distinguen() {
        assertTrue(Materia("Aprobada", listOf(4.0, 4.0, 4.0)).aprobada)
        assertTrue(!Materia("Baja", listOf(2.5, 2.8, 3.0)).aprobada)
    }

    @Test
    fun formato_de_nota_usa_un_decimal() {
        assertEquals("4.2", 4.2333.formatearNota())
        assertEquals("4.0", 4.0.formatearNota())
    }
}
