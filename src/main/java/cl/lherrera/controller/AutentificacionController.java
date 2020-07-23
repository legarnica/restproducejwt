package cl.lherrera.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.lherrera.model.dto.LoginFormDto;
import cl.lherrera.model.dto.TokenDto;
import cl.lherrera.service.AutentificacionService;

@RestController
@RequestMapping("/api/v1/login")
public class AutentificacionController {
    
    @Autowired
    private AutentificacionService servicio;
    
    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public TokenDto usuarios(@RequestBody LoginFormDto loginForm) {
        return servicio.autentificar(loginForm.getUsername(), loginForm.getPassword());
        
    }
}
