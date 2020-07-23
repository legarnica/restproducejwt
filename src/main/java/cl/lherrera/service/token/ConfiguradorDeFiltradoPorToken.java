package cl.lherrera.service.token;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class ConfiguradorDeFiltradoPorToken 
        extends SecurityConfigurerAdapter <DefaultSecurityFilterChain, HttpSecurity>{

    
    private ProveedorDeToken jwtTokenProvider;

    public ConfiguradorDeFiltradoPorToken(ProveedorDeToken jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        FiltraPorCadaRequestConToken miFiltroToken = new FiltraPorCadaRequestConToken(jwtTokenProvider);
        http.addFilterBefore(miFiltroToken, UsernamePasswordAuthenticationFilter.class);
    }
    
}
