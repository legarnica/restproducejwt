package cl.lherrera.service.token;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import cl.lherrera.model.entity.Rol;
import cl.lherrera.service.UsuarioSistemaService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class ProveedorDeToken {
    private Logger logger = LoggerFactory.getLogger(ProveedorDeToken.class);
    
    private static final String BEARER = "Bearer ";
    
    @Value("${token.clave-secreta}")
    private String claveSecreta;
    
    @Value("${token.tiempo-expiracion}")
    private Integer tiempoExpiracion;
    
    @Autowired
    private UsuarioSistemaService servicioUsuarioSistema;
    
    @PostConstruct
    public void iniciarClaveSecretaDespuesQueElBeanLoInicia() {
        claveSecreta = Base64.getEncoder().encodeToString(claveSecreta.getBytes());
        logger.info("LLave inicializada: " + claveSecreta);
    }
    
    public String fabricarToken(String username, List<Rol> roles) {
        // seteamos el userName al mapa de argumentos del token
        Claims claims = Jwts.claims().setSubject(username);
        // ingresamos los roles
        claims.put("auth", UsuarioSistemaService.obtenerRolesDeSistema(roles));
        
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + tiempoExpiracion);
        
        return Jwts.builder() // es un string
                .setClaims(claims)
                .setIssuedAt(ahora)
                .setExpiration(expiracion)
                .signWith(SignatureAlgorithm.HS256, claveSecreta)
                .compact(); 
    }
    
    /**
     * Usa (Jwts.parser()) para entrar al token y decodificarlo
     * para obtener el nombre de usuario
     */
    public String obtenerNombreDeUsuarioDeToken(String token) {
        return Jwts.parser()
                .setSigningKey(claveSecreta)
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // este es el userName

    }
    
    public boolean validaElToken(String token) {
        boolean esValido = false;
        try {
            Jwts.parser().setSigningKey(claveSecreta).parseClaimsJws(token);
            esValido = true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.warn("El token ya no es válido");
        }

        return esValido;
    }
    
    
    public String resuelveBearer(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        
        if (bearerToken != null && bearerToken.startsWith(BEARER))
            bearerToken = bearerToken.substring(BEARER.length());
        
        return bearerToken;
    }
    
    /**
     * crea la autentificacion (UsernamePasswordAuthenticationToken)
     */
    public Authentication obtenerAutentificacion(String token) {
        // obtenemos el (User) UserDetails, el usuario del sistema
        UserDetails userDetails = servicioUsuarioSistema.loadUserByUsername(obtenerNombreDeUsuarioDeToken(token));
        
        UsernamePasswordAuthenticationToken autenticacion;
        autenticacion = new UsernamePasswordAuthenticationToken(
                userDetails, 
                "", // el password no se pasa por que ya está autentificado
                userDetails.getAuthorities()
        );
        return autenticacion;
    }

}
