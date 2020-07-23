package cl.lherrera.service.token;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.*;
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
    public void iniciarClaveSecretaDespuesQueElConstructorInicia() {
        claveSecreta = Base64.getEncoder().encodeToString(claveSecreta.getBytes());
        logger.info("clave secreta inicializada: " +  claveSecreta);
    }
    
    public String fabricaToken(String username, List<Rol> roles) {
        // ingresamos el nombre de usuario y roles
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", UsuarioSistemaService.obtenerRolesDeSistema(roles));
        // setear la duración del token
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + tiempoExpiracion);
        
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(ahora)
                .setExpiration(expiracion)
                .signWith(SignatureAlgorithm.HS256, claveSecreta)
                .compact();
        
        return token;
    }
    
    public String obtenerNombreDeUsuarioDeToken(String token) {
        String nombreUsuarioDeToken = Jwts.parser()
                .setSigningKey(claveSecreta)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        
        return nombreUsuarioDeToken;
    }
    
    public boolean validaElToken(String token) {
        boolean esValido = false;
        
        try {
            Jwts.parser().setSigningKey(claveSecreta).parseClaimsJws(token);
            esValido = true;
            
        }catch (JwtException e) {
            logger.warn("El token no es válido");
        }catch (IllegalArgumentException e) {
            logger.warn("Error de argumentos");
        }
        return esValido;
    }
    
    public String resuelveBearer(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        
        if(bearerToken != null && bearerToken.startsWith(BEARER))
            bearerToken = bearerToken.substring(BEARER.length());
        
        return bearerToken;
    }
    
    public Authentication obtenerAutentificacion(String token) {
        String username = obtenerNombreDeUsuarioDeToken(token);
        UserDetails userDetails = servicioUsuarioSistema
                .loadUserByUsername(username); // donde se hace el login
        UsernamePasswordAuthenticationToken autentificacion;
        autentificacion = new UsernamePasswordAuthenticationToken(
                userDetails, 
                "", 
                userDetails.getAuthorities()
        );
        
        return autentificacion;
    }
    
}




























