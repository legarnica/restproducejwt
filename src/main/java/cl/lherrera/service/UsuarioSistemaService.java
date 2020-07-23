package cl.lherrera.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cl.lherrera.model.dao.UsuarioDao;
import cl.lherrera.model.entity.Rol;
import cl.lherrera.model.entity.Usuario;

@Service
public class UsuarioSistemaService implements UserDetailsService{

    @Autowired
    private UsuarioDao dao;
    
    /**
     * crea al usuario del sistema
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User usuarioSistema = null;
        Usuario usuario = dao.findByUsername(username).orElse(null);
        if(usuario != null) {
            List<Rol> roles = usuario.getRoles();
            String password = usuario.getPassword();
            // acá se intenta el login
            usuarioSistema = new User(username, password, obtenerRolesDeSistema(roles));
        }else {
            throw new UsernameNotFoundException(username);
        }
        
        return usuarioSistema;
    }

    /**
     * transforma los roles de la entidad
     * a roles de sistema. 
     */
    public static List<SimpleGrantedAuthority> obtenerRolesDeSistema(List<Rol> roles_){
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        for(Rol rol: roles_)
            roles.add(new SimpleGrantedAuthority(rol.toString()));
        
        return roles;
    }

}
