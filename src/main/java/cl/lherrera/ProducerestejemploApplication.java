package cl.lherrera;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import cl.lherrera.model.dao.UsuarioDao;
import cl.lherrera.model.entity.Rol;
import cl.lherrera.model.entity.Usuario;

@SpringBootApplication
public class ProducerestejemploApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerestejemploApplication.class, args);
	}

}

@Component
class AppStartupRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(AppStartupRunner.class);

    @Autowired
    private UsuarioDao dao;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Your application started with option names : {}", args.getOptionNames());
        Usuario usuario = new Usuario();
        
        usuario.setNombre("Luis Herrera");
        usuario.setUsername("luis@mail.cl");
        usuario.setPassword("1234");
        usuario.setRoles(Arrays.asList(Rol.ROLE_ADMIN));
        dao.save(usuario);
        
    }
    
    
}