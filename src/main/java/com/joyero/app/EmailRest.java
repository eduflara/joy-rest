package com.joyero.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app/email")
public class EmailRest {

    @Autowired
    private EmailSrv emailSrv;

    @GetMapping(value = "/test", produces = {"application/json"})
    public String get(@RequestParam String destinatario) {
        emailSrv.enviar("prueba", "prueba de correo", "test@sumainfo.es", destinatario);
        return "";
    }
}
