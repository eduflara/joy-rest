package com.joyero.app.seguridad.grupo;

import com.joyero.base.ControladorRest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app/seg/grupo")
public class GrupoRest extends ControladorRest<Grupo, Long> {

    @Override
    public void preGuardar(Grupo entidad) {

    }

    @Override
    public void preActualizar(Grupo entidad) {

    }


    @GetMapping(value = "/nombre/{nombre}")
    @ResponseBody
    public Grupo findByNombre(@PathVariable String nombre) {
        GrupoRepository grupoRepository = (GrupoRepository) repository;
        return grupoRepository.findByNombre(nombre);
    }

    @GetMapping(value = "/usuario/{idUsuario}")
    @ResponseBody
    public List<Grupo> findByUsuario(@PathVariable Long idUsuario) {
        GrupoRepository grupoRepository = (GrupoRepository) repository;
        return grupoRepository.findByUsuario(idUsuario);
    }
}
