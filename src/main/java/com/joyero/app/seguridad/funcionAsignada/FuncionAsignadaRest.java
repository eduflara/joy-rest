package com.joyero.app.seguridad.funcionAsignada;

import com.joyero.base.ControladorRest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app/seg/funcionAsignada")
public class FuncionAsignadaRest extends ControladorRest<FuncionAsignada, Long> {

    @Override
    public void preGuardar(FuncionAsignada entidad) {
    }

    @Override
    public void preActualizar(FuncionAsignada entidad) {
    }


    @GetMapping(value = "/grupo/{idGrupo}")
    @ResponseBody
    public List<FuncionAsignada> findByGrupo(@PathVariable long idGrupo) {
        FuncionAsignadaRepository funcionAsignadaRepository = (FuncionAsignadaRepository) repository;
        return funcionAsignadaRepository.findByGrupo(idGrupo);
    }
}
