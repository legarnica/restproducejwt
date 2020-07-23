package cl.lherrera.service.token;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class FiltraPorCadaRequestConToken extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(FiltraPorCadaRequestConToken.class);

    private ProveedorDeToken proveedorToken;
    
    public FiltraPorCadaRequestConToken(ProveedorDeToken proveedorToken) {
        this.proveedorToken = proveedorToken;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = proveedorToken.resuelveBearer(request);
        
        if(token != null && proveedorToken.validaElToken(token)) {
            Authentication auth = proveedorToken.obtenerAutentificacion(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }else {
            logger.warn("no fue posible autentificar :( ");
        }
        
        filterChain.doFilter(request, response);
    }

}
