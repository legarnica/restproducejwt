package cl.lherrera.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.lherrera.model.dto.UsuarioFormDto;
import cl.lherrera.model.dto.UsuariosDto;
import cl.lherrera.model.entity.Usuario;
import cl.lherrera.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService servicio;
    
    @CrossOrigin
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UsuariosDto usuarios() {
        return servicio.obtenerTodos();
        
    }
    
    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuariosDto registrar(@RequestBody UsuarioFormDto usuarioForm) {
        return servicio.registrar(usuarioForm);
    }

    @CrossOrigin
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UsuariosDto actualizar(@RequestBody Usuario usuario) {
        return servicio.actualizar(usuario);
    }
    
    @CrossOrigin
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public UsuariosDto eliminar(@RequestBody Usuario usuario) {
        
        return servicio.eliminar(usuario);
    }
    
    @CrossOrigin
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuariosDto obtenerUsuario(@PathVariable Integer id) {
        return servicio.obtenerUsuario(id);
    }

}
