package com.cursos.cursos.repository;

import com.cursos.cursos.model.entity.InscripcionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InscripcionesRepository extends JpaRepository<InscripcionesEntity, Integer> {
    List<InscripcionesEntity> findByIdUsuario(int idUsuario);
    List<InscripcionesEntity> findByIdCurso(int idCurso);
    boolean existsByIdUsuarioAndIdCurso(int idUsuario, int idCurso);
    InscripcionesEntity findByIdUsuarioAndIdCurso(int idUsuario, int idCurso);
}