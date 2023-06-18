package com.joyero.app.contrato;
import com.joyero.base.ControladorRest;
import com.joyero.base.exception.ResultadoException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app/contrato")
public class ContratoRest extends ControladorRest<Contrato, Long> {

    /* Sobrescribir métodos comunes */
    @Override
    protected void preGuardar(Contrato entidad) {
    }

    @Override
    protected void postGuardar(Contrato entidad) {
    }

    @Override
    protected void preActualizar(Contrato entidad) {
    }

    @Override
    protected void postActualizar(Contrato entidad) {
    }

    @Override
    protected void preEliminar(Long id) {
    }

    @Override
    protected void postEliminar(Long id) {
    }

    @Override
    protected ResultadoException validaGuardar(Contrato entidad) {
        return super.validaGuardar(entidad);
    }

    @Override
    protected ResultadoException validaActualizar(Contrato entidad) {
        return super.validaActualizar(entidad);
    }

    @Override
    protected ResultadoException validaEliminar(Long id) {
        return super.validaEliminar(id);
    }
}