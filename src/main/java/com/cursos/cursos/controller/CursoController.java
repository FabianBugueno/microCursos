package com.cursos.cursos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cursos.cursos.model.Cursos;
import com.cursos.cursos.service.CursoService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class CursoController {
    @Autowired
    private CursoService cursoService;
    @Operation(summary = "Crear un nuevo curso")
    @PostMapping("/crearCurso") 
    public ResponseEntity<String> crearCurso(@RequestBody Cursos curso){
        return ResponseEntity.ok(cursoService.crearCurso(curso));
    }
    @Operation(summary = "Obtener un curso por ID")
    @GetMapping("/obtenerCurso/{idCurso}")
    public ResponseEntity<Cursos> obtenerCurso(@PathVariable int idCurso) {
        Cursos curso = cursoService.obtenerCurso(idCurso);
        if (curso != null) {
            return ResponseEntity.ok(curso);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "Eliminar un curso por ID")
    @DeleteMapping("/eliminarCurso/{idCurso}")
    public ResponseEntity<String> eliminarCurso(@PathVariable int idCurso) {
        String respuesta = cursoService.eliminarCurso(idCurso);
        if (respuesta.equals("Curso eliminado correctamente")) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(404).body(respuesta);
        }
    }
    @Operation(summary = "Modificar un curso por ID")
    @PutMapping("/modificarCurso/{idCurso}")
    public ResponseEntity<String> modificarCurso(@PathVariable int idCurso, @RequestBody Cursos curso) {
        String respuesta = cursoService.modificarCurso(idCurso, curso);
        if (respuesta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(respuesta);
    }
}
