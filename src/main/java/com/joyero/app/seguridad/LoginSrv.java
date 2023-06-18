/*
 * Copyright (c) 2019.
 * LoginSrv.java
 * 29/11/18 13:30
 * alejandro
 */

package com.joyero.app.seguridad;

import com.joyero.app.seguridad.usuario.Usuario;
import com.joyero.app.seguridad.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginSrv implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = null;
        try {
            usuario = usuarioRepository.findByUsername(username);


//
//            List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
//            usuario.getAuthorities().addAll(authorities);

            if (usuario == null) {
                throw new UsernameNotFoundException(username);
            }
        } catch (DataAccessException ex) {
            throw new UsernameNotFoundException(username, ex);
        }
        return usuario;
    }
}
