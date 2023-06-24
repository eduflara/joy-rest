package com.joyero.app.lote;
import com.joyero.base.ControladorRest;
import com.joyero.base.exception.ResultadoException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app/lote")
public class LoteRest extends ControladorRest<Lote, Long> {

    /* Sobrescribir métodos comunes */
    @Override
    protected void preGuardar(Lote entidad) {
    }

    @Override
    protected void postGuardar(Lote entidad) {
    }

    @Override
    protected void preActualizar(Lote entidad) {
    }

    @Override
    protected void postActualizar(Lote entidad) {
    }

    @Override
    protected void preEliminar(Long id) {
    }

    @Override
    protected void postEliminar(Long id) {
    }

    @Override
    protected ResultadoException validaGuardar(Lote entidad) {
        return super.validaGuardar(entidad);
    }

    @Override
    protected ResultadoException validaActualizar(Lote entidad) {
        return super.validaActualizar(entidad);
    }

    @Override
    protected ResultadoException validaEliminar(Long id) {
        return super.validaEliminar(id);
    }
}