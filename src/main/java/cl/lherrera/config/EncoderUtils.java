package cl.lherrera.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * clase utilitaria 
 * @author luisherrera
 *
 */
public class EncoderUtils {

    /**
     * constructor privado para no crear
     * instancias de estas clases
     * utilitarias.
     */
    private EncoderUtils() {}
    
    /**
     * método para el encoding
     * @return
     */
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
