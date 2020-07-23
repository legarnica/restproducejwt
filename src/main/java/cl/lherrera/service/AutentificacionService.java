package cl.lherrera.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import cl.lherrera.model.dao.UsuarioDao;
import cl.lherrera.model.dto.TokenDto;
import cl.lherrera.model.entity.Usuario;
import cl.lherrera.service.token.ProveedorDeToken;


@Service
public class AutentificacionService {
    private Logger logger = LoggerFactory.getLogger(AutentificacionService.class);
    
    @Autowired
    private ProveedorDeToken proveedorToken;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UsuarioDao dao;

    public TokenDto autentificar(String username, String password) {
        TokenDto tokenDto = new TokenDto();
        String token = "";
        try {
            Authentication objetoAutentificacion = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(objetoAutentificacion);
            
            Usuario usuarioAutenticado = dao.findByUsername(username).orElse(null);
            token = generaToken(usuarioAutenticado);
            tokenDto.setStatus(200);
            tokenDto.setMensaje("OK");
            tokenDto.setToken(token);

        } catch (AuthenticationException e) {
            logger.warn("Error al autentificar");
            tokenDto.setStatus(500);
            tokenDto.setMensaje("Error al autentificar");
            tokenDto.setToken(null);
        }
        
        return tokenDto;
    }
    
    private String generaToken(Usuario usuario) {
        String token = proveedorToken.fabricarToken (
            usuario.getUsername(),
            usuario.getRoles()
        );

        return token;
    }
    
}
