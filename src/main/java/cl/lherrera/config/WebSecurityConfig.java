package cl.lherrera.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import cl.lherrera.service.token.ConfiguradorDeFiltradoPorToken;
import cl.lherrera.service.token.ProveedorDeToken;

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    ProveedorDeToken proveedorToken;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // se elimina el csrfToken (inseguro)
        http.csrf().disable();
        // declaramos que este sistema es
        // un sistema sin estado
        http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        // dejar disponible el login, y cerramos todo lo demás
        http.authorizeRequests()
        .antMatchers("/api/v1/login").permitAll()
        .anyRequest().authenticated();
        // manejamos la respuesta default en caso de error
        http.exceptionHandling().accessDeniedPage("/api/v1/login");
        
        // Aplicamos nuestra configuración del token
        http.apply(new ConfiguradorDeFiltradoPorToken(proveedorToken));
        
    }

    /**
     * siempre va y es igual.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
}
