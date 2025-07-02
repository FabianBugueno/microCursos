package com.cursos.cursos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inscripciones {
    private int idInscripcion;
    private int idCurso;
    private int idUsuario;
    private Boolean estado;
}
