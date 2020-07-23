package cl.lherrera.service.token;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class ConfiguradorDeFiltradoPorToken extends SecurityConfigurerAdapter <DefaultSecurityFilterChain, HttpSecurity>{

    private ProveedorDeToken proveedorToken;

    public ConfiguradorDeFiltradoPorToken(ProveedorDeToken proveedorToken) {
        this.proveedorToken = proveedorToken;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        FiltraPorCadaRequestConToken miFiltroToken =  new FiltraPorCadaRequestConToken(proveedorToken);
        builder.addFilterBefore(miFiltroToken, UsernamePasswordAuthenticationFilter.class);
    }

}
