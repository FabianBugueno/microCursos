package com.cursos.cursos.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cursos.cursos.model.entity.InscripcionesEntity;
import com.cursos.cursos.service.InscripcionesService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class InscripcionesController {
    @Autowired
    private InscripcionesService inscripcionesService;
    @Operation(summary = "Obtener cursos por id de usuario")
    @GetMapping ("/obtenerCursosporUsuarios/{Id}")
    public List<Map<String, Object>> obtenerCursosPorUsuario(@PathVariable int Id) {
        return inscripcionesService.obtenerCursosPorUsuario(Id);
    }
    @Operation(summary = "Inscribir usuario a un curso")
    @PostMapping("/inscribir")
    public String inscribirUsuarioACurso(@RequestParam int idUsuario, @RequestParam int idCurso) {
        return inscripcionesService.inscribirUsuarioACurso(idUsuario, idCurso);
    }
    @Operation(summary = "Eliminar inscripción por ID")
    @DeleteMapping("/eliminarInscripcion/{idInscripcion}")
    public String eliminarInscripcion(@PathVariable int idInscripcion) {
        return inscripcionesService.eliminarInscripcion(idInscripcion);
    }
    @Operation(summary = "Obtener usuarios por id de curso")
    @GetMapping("/obtenerUsuariosPorCurso/{idCurso}")
    public List<Map<String, Object>> obtenerUsuariosPorCurso(@PathVariable int idCurso) {
        return inscripcionesService.obtenerUsuariosPorCurso(idCurso);
    }
    @Operation(summary = "Desinscribir usuario de un curso")
    @DeleteMapping("/desinscribir")
    public String desinscribirUsuarioDeCurso(@RequestParam int idUsuario, @RequestParam int idCurso) {
        return inscripcionesService.desinscribirUsuarioDeCurso(idUsuario, idCurso);
    }
    @Operation(summary = "Modificar inscripción por usuario y curso")
    @PutMapping("/modificarInscripcion")
    public ResponseEntity<String> modificarInscripcion(
            @RequestParam int idUsuario,
            @RequestParam int idCurso,
            @RequestBody InscripcionesEntity inscripcion) {
        String respuesta = inscripcionesService.modificarInscripcion(idUsuario, idCurso, inscripcion);
        if (respuesta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(respuesta);
    }
}
