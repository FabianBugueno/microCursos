package com.cursos.cursos.repository;

import com.cursos.cursos.model.entity.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<CursoEntity, Integer> {
    CursoEntity findByIdCurso(int idCurso);
    Boolean existsByNombre(String nombre);
    void deleteByIdCurso(int idCurso);
    
}
