package com.joyero.app.cliente;
import com.joyero.app.Cliente;
import com.joyero.base.ControladorRest;
import com.joyero.base.exception.ResultadoException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app/cliente")
public class ClienteRest extends ControladorRest<Cliente, Long> {

    /* Sobrescribir métodos comunes */
    @Override
    protected void preGuardar(Cliente entidad) {
    }

    @Override
    protected void postGuardar(Cliente entidad) {
    }

    @Override
    protected void preActualizar(Cliente entidad) {
    }

    @Override
    protected void postActualizar(Cliente entidad) {
    }

    @Override
    protected void preEliminar(Long id) {
    }

    @Override
    protected void postEliminar(Long id) {
    }

    @Override
    protected ResultadoException validaGuardar(Cliente entidad) {
        return super.validaGuardar(entidad);
    }

    @Override
    protected ResultadoException validaActualizar(Cliente entidad) {
        return super.validaActualizar(entidad);
    }

    @Override
    protected ResultadoException validaEliminar(Long id) {
        return super.validaEliminar(id);
    }
}