package com.cursos.cursos.controller;

import com.cursos.cursos.model.entity.InscripcionesEntity;
import com.cursos.cursos.service.InscripcionesService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InscripcionesController.class)
class InscripcionesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InscripcionesService inscripcionesService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Obtener cursos por ID de usuario - con resultados")
    void obtenerCursosPorUsuario_conResultados() throws Exception {
        List<Map<String, Object>> cursos = List.of(
                Map.of("idCurso", 1, "nombreCurso", "Matemáticas"));
        when(inscripcionesService.obtenerCursosPorUsuario(1)).thenReturn(cursos);

        mockMvc.perform(get("/obtenerCursosporUsuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreCurso").value("Matemáticas"));
    }

    @Test
    @DisplayName("Inscribir usuario a curso - éxito")
    void inscribirUsuarioACurso_ok() throws Exception {
        when(inscripcionesService.inscribirUsuarioACurso(1, 2)).thenReturn("Usuario inscrito correctamente");

        mockMvc.perform(post("/inscribir")
                .param("idUsuario", "1")
                .param("idCurso", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario inscrito correctamente"));
    }

    @Test
    @DisplayName("Eliminar inscripción - éxito")
    void eliminarInscripcion_ok() throws Exception {
        when(inscripcionesService.eliminarInscripcion(10)).thenReturn("Inscripción eliminada correctamente");

        mockMvc.perform(delete("/eliminarInscripcion/10"))
                .andExpect(status().isOk())
                .andExpect(content().string("Inscripción eliminada correctamente"));
    }

    @Test
    @DisplayName("Obtener usuarios por curso - sin resultados")
    void obtenerUsuariosPorCurso_vacio() throws Exception {
        when(inscripcionesService.obtenerUsuariosPorCurso(5)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/obtenerUsuariosPorCurso/5"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("Desinscribir usuario - éxito")
    void desinscribirUsuarioDeCurso_ok() throws Exception {
        when(inscripcionesService.desinscribirUsuarioDeCurso(3, 4)).thenReturn("Usuario desinscrito correctamente");

        mockMvc.perform(delete("/desinscribir")
                .param("idUsuario", "3")
                .param("idCurso", "4"))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario desinscrito correctamente"));
    }

    @Test
    @DisplayName("Modificar inscripción - éxito")
    void modificarInscripcion_ok() throws Exception {
        InscripcionesEntity inscripcion = new InscripcionesEntity();
        inscripcion.setIdUsuario(1);
        inscripcion.setIdCurso(2);
        inscripcion.setEstado(false);
        when(inscripcionesService.modificarInscripcion(eq(1), eq(2), any())).thenReturn("Inscripción modificada correctamente");

        mockMvc.perform(put("/modificarInscripcion")
                .param("idUsuario", "1")
                .param("idCurso", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inscripcion)))
                .andExpect(status().isOk())
                .andExpect(content().string("Inscripción modificada correctamente"));
    }

    @Test
    @DisplayName("Modificar inscripción - no encontrada")
    void modificarInscripcion_noExiste() throws Exception {
        InscripcionesEntity inscripcion = new InscripcionesEntity();
        when(inscripcionesService.modificarInscripcion(eq(99), eq(100), any())).thenReturn(null);

        mockMvc.perform(put("/modificarInscripcion")
                .param("idUsuario", "99")
                .param("idCurso", "100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inscripcion)))
                .andExpect(status().isNotFound());
    }
}
