package com.cursos.cursos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursos.cursos.model.Cursos;
import com.cursos.cursos.model.entity.CursoEntity;
import com.cursos.cursos.repository.CursoRepository;

@Service
public class CursoService {
    @Autowired
    public CursoRepository cursoRepository;

    public String crearCurso(Cursos curso){
        try{

            boolean existe = cursoRepository.existsByNombre(curso.getNombre());
            if (!existe){
                CursoEntity cursoNuevo = new CursoEntity();
                cursoNuevo.setNombre(curso.getNombre());
                cursoNuevo.setDescripcion(curso.getDescripcion());
                cursoNuevo.setPrecio(curso.getPrecio());
                cursoNuevo.setEstado(curso.getEstado());
                cursoRepository.save(cursoNuevo);
                return "Curso creado correctamente";
            }
            return "El curso ya existe";
        }catch (Exception e){
            return "Error al crear el curso: " + e.getMessage();
        }
    }
    
    public Cursos obtenerCurso(int idCurso){
        try{
            CursoEntity curso = cursoRepository.findByIdCurso(idCurso);
            if (curso != null) {
                Cursos curse = new Cursos(
                    curso.getIdCurso(),
                    curso.getNombre(),
                    curso.getDescripcion(),
                    curso.getPrecio(),
                    curso.getEstado()  
                );
                return curse;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public String eliminarCurso(int idCurso) {
        try {
            if (cursoRepository.existsById(idCurso)) {
                cursoRepository.deleteById(idCurso);
                return "Curso eliminado correctamente";
            } else {
                return "El curso no existe";
            }
        } catch (Exception e) {
            return "Error al eliminar el curso: " + e.getMessage();
        }
    }

    public String modificarCurso(int idCurso, Cursos curso) {
        CursoEntity cursoExistente = cursoRepository.findByIdCurso(idCurso);
        if (cursoExistente == null) {
            return null;
        }
        cursoExistente.setNombre(curso.getNombre());
        cursoExistente.setDescripcion(curso.getDescripcion());
        cursoExistente.setPrecio(curso.getPrecio());
        cursoExistente.setEstado(curso.getEstado());
        cursoRepository.save(cursoExistente);
        return "Curso modificado correctamente";
    }
}