/*
 * Copyright (c) 2019.
 * LoginRestCtl.java
 * 3/12/18 17:16
 * alejandro
 */

package com.joyero.app.seguridad;

import com.joyero.app.seguridad.usuario.Usuario;
import com.joyero.app.seguridad.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoginRestCtl {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping(value = "/login/{username}")
    public Boolean login(@PathVariable String username) {
        Usuario usuario = repository.findByUsername(username);
        return usuario != null;
    }


    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization")
                .substring("Basic".length()).trim();
        return () -> new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0];
    }
}
