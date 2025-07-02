package com.cursos.cursos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cursos {
    private int idCurso;
    private String nombre;
    private String descripcion;
    private int precio;
    private Boolean estado;

}