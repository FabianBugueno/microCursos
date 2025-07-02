package com.cursos.cursos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

import com.cursos.cursos.model.entity.CursoEntity;
import com.cursos.cursos.model.entity.InscripcionesEntity;
import com.cursos.cursos.repository.CursoRepository;
import com.cursos.cursos.repository.InscripcionesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class InscripcionesService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private InscripcionesRepository inscripcionesRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public List<Map<String, Object>> obtenerCursosPorUsuario(int userId){
        List<InscripcionesEntity> inscripciones = inscripcionesRepository.findByIdUsuario(userId);
        List<Map<String, Object>> resultado = new ArrayList<>();
        for (InscripcionesEntity insc : inscripciones) {
            Map<String, Object> datos = new HashMap<>();
            datos.put("idCurso", insc.getIdCurso());
            // Busca el curso por id y obtiene el nombre
            CursoEntity curso = cursoRepository.findByIdCurso(insc.getIdCurso());
            datos.put("nombreCurso", curso != null ? curso.getNombre() : "No encontrado");
            resultado.add(datos);
        }
        return resultado;
    }
    
    public String inscribirUsuarioACurso(int idUsuario, int idCurso) {
    try {
        // Validar si el curso existe
        if (!cursoRepository.existsById(idCurso)) {
            return "El curso no existe";
        }
        // Validar si ya está inscrito
        if (inscripcionesRepository.existsByIdUsuarioAndIdCurso(idUsuario, idCurso)) {
            return "El usuario ya está inscrito en este curso";
        }
        InscripcionesEntity inscripcion = new InscripcionesEntity();
        inscripcion.setIdUsuario(idUsuario);
        inscripcion.setIdCurso(idCurso);
        inscripcion.setEstado(true);
        inscripcionesRepository.save(inscripcion);
        return "Usuario inscrito correctamente";
    } catch (Exception e) {
        return "Error al inscribir usuario: " + e.getMessage();
    }
}

public String eliminarInscripcion(int idInscripcion) {
    try {
        if (inscripcionesRepository.existsById(idInscripcion)) {
            inscripcionesRepository.deleteById(idInscripcion);
            return "Inscripción eliminada correctamente";
        } else {
            return "La inscripción no existe";
        }
    } catch (Exception e) {
        return "Error al eliminar inscripción: " + e.getMessage();
    }
}

public List<Map<String, Object>> obtenerUsuariosPorCurso(int idCurso) {
    List<InscripcionesEntity> inscripciones = inscripcionesRepository.findByIdCurso(idCurso);
    List<Map<String, Object>> resultado = new ArrayList<>();
    for (InscripcionesEntity insc : inscripciones) {
        Map<String, Object> datos = new HashMap<>();
        datos.put("idUsuario", insc.getIdUsuario());
        String url = "http://localhost:8080/obtenerUsuarioDto/" + insc.getIdUsuario();
        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
            );
            Map<String, Object> usuario = response.getBody();
            if (usuario != null && usuario.get("nombre") != null) {
                datos.put("nombreUsuario", usuario.get("nombre"));
            } else {
                datos.put("nombreUsuario", "No encontrado");
            }
        } catch (Exception e) {
            datos.put("nombreUsuario", "No encontrado");
        }
        resultado.add(datos);
    }
    return resultado;
}

public String desinscribirUsuarioDeCurso(int idUsuario, int idCurso) {
    try {
        InscripcionesEntity inscripcion = inscripcionesRepository.findByIdUsuarioAndIdCurso(idUsuario, idCurso);
        if (inscripcion != null) {
            inscripcionesRepository.delete(inscripcion);
            return "Usuario desinscrito correctamente";
        } else {
            return "No existe inscripción para ese usuario y curso";
        }
    } catch (Exception e) {
        return "Error al desinscribir usuario: " + e.getMessage();
    }
}

public String modificarInscripcion(int idUsuario, int idCurso, InscripcionesEntity inscripcion) {
    InscripcionesEntity inscripcionExistente = inscripcionesRepository.findByIdUsuarioAndIdCurso(idUsuario, idCurso);
    if (inscripcionExistente == null) {
        return null;
    }
    inscripcionExistente.setEstado(inscripcion.getEstado());
    inscripcionesRepository.save(inscripcionExistente);
    return "Inscripción modificada correctamente";
}
}