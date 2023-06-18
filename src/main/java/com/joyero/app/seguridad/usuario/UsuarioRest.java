package com.joyero.app.seguridad.usuario;


import com.joyero.base.ControladorRest;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app/seg/usuario")
public class UsuarioRest extends ControladorRest<Usuario, Long> {

    @Override
    public void preGuardar(Usuario entidad) {

    }

    @Override
    public void preActualizar(Usuario entidad) {

    }

    @Override
    protected void postGuardar(Usuario entidad) {

    }

    @Override
    public void preEliminar(Long aLong) {

    }

    @GetMapping(value = "/username/{username}")
    @ResponseBody
    public Usuario findByUsername(@PathVariable String username) {
        UsuarioRepository usuarioRepository = (UsuarioRepository) repository;
        return usuarioRepository.findByUsername(username);
    }

}
