package com.cursos.cursos.controller;

import com.cursos.cursos.model.Cursos;
import com.cursos.cursos.service.CursoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CursoController.class)
class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoService cursoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Crear curso - éxito")
    void crearCurso_ok() throws Exception {
        Cursos curso = new Cursos(1, "Curso Java", "Curso para aprender Java", 100, true);
        when(cursoService.crearCurso(any())).thenReturn("Curso creado exitosamente");

        mockMvc.perform(post("/crearCurso")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(curso)))
                .andExpect(status().isOk())
                .andExpect(content().string("Curso creado exitosamente"));
    }

    @Test
    @DisplayName("Obtener curso por ID - encontrado")
    void obtenerCurso_existente() throws Exception {
        Cursos curso = new Cursos(1, "Curso Spring", "Curso para aprender Spring", 200, true);
        when(cursoService.obtenerCurso(1)).thenReturn(curso);

        mockMvc.perform(get("/obtenerCurso/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Curso Spring"))
                .andExpect(jsonPath("$.descripcion").value("Curso para aprender Spring"));
    }

    @Test
    @DisplayName("Obtener curso por ID - no encontrado")
    void obtenerCurso_noExistente() throws Exception {
        when(cursoService.obtenerCurso(99)).thenReturn(null);

        mockMvc.perform(get("/obtenerCurso/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Modificar curso - éxito")
    void modificarCurso_ok() throws Exception {
        Cursos curso = new Cursos(1, "Curso Spring Boot", "Curso para aprender Spring Boot", 300, true);
        when(cursoService.modificarCurso(eq(1), any())).thenReturn("Curso modificado exitosamente");

        mockMvc.perform(put("/modificarCurso/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(curso)))
                .andExpect(status().isOk())
                .andExpect(content().string("Curso modificado exitosamente"));
    }

    @Test
    @DisplayName("Modificar curso - no encontrado")
    void modificarCurso_noExiste() throws Exception {
        when(cursoService.modificarCurso(eq(999), any())).thenReturn(null);

        mockMvc.perform(put("/modificarCurso/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Cursos())))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Eliminar curso - éxito")
    void eliminarCurso_ok() throws Exception {
        when(cursoService.eliminarCurso(1)).thenReturn("Curso eliminado correctamente");

        mockMvc.perform(delete("/eliminarCurso/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Curso eliminado correctamente"));
    }

    @Test
    @DisplayName("Eliminar curso - no encontrado")
    void eliminarCurso_noExiste() throws Exception {
        when(cursoService.eliminarCurso(404)).thenReturn("Curso no encontrado");

        mockMvc.perform(delete("/eliminarCurso/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Curso no encontrado"));
    }

    @Test
    @DisplayName("Eliminar curso - fallo en eliminación")
    void eliminarCurso_fallo() throws Exception {
        when(cursoService.eliminarCurso(2)).thenReturn("Error al eliminar el curso");

        mockMvc.perform(delete("/eliminarCurso/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error al eliminar el curso"));
    }
}
