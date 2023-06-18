package com.joyero.app.seguridad.funcion;

import com.joyero.base.ControladorRest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app/seg/funcion")
public class FuncionRest extends ControladorRest<Funcion, Long> {

    @Override
    public void preGuardar(Funcion entidad) {

    }

    @Override
    public void preActualizar(Funcion entidad) {

    }


    @GetMapping(value = "/superiores")
    public List<Funcion> getFuncionesSuperiores() {
        FuncionRepository funcionRepository = (FuncionRepository) repository;
        return funcionRepository.getAllByFuncionSuperiorIsNullAndApareceMenuIsTrue();
    }

    @GetMapping(value = "/inferiores/{id}")
    public List<Funcion> getFuncionesInferiores(@PathVariable Long id) {
        FuncionRepository funcionRepository = (FuncionRepository) repository;
        return funcionRepository.getAllByIdFuncionSuperior(id);
    }

}
