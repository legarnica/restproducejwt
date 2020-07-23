package cl.lherrera.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.lherrera.model.dao.UsuarioDao;
import cl.lherrera.model.dto.UsuarioFormDto;
import cl.lherrera.model.dto.UsuariosDto;
import cl.lherrera.model.entity.Usuario;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioDao usuarioDao;
    
    @Transactional(readOnly = true)
    public UsuariosDto obtenerTodos() {
        return new UsuariosDto(200, usuarioDao.findAll());
    }
    
    @Transactional
    public UsuariosDto registrar(UsuarioFormDto usuarioForm) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioForm.getNombre());
        usuario.setUsername(usuarioForm.getUsername());
        usuario.setPassword(usuarioForm.getPassword());
        Usuario respuesta = usuarioDao.save(usuario);
        
        return new UsuariosDto(201, Arrays.asList(respuesta));
    }
    
    @Transactional
    public UsuariosDto actualizar(Usuario usuario) {
        Usuario respuesta = usuarioDao.save(usuario);

        return new UsuariosDto(200, Arrays.asList(respuesta));
    }
    
    @Transactional
    public UsuariosDto eliminar(Usuario usuario) {
        usuarioDao.delete(usuario);

        return new UsuariosDto(200, Arrays.asList(usuario));
    }
    
    @Transactional(readOnly = true)
    public UsuariosDto obtenerUsuario(Integer id) {
        UsuariosDto respuesta = new UsuariosDto();
        respuesta.setStatus(200);
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario = usuarioDao.findById(id).orElse(null);
        usuarios = Arrays.asList(usuario);
        
        respuesta.setData(usuarios);
        
        return respuesta;
    }
    
}
