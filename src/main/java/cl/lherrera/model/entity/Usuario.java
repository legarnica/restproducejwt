package cl.lherrera.model.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import cl.lherrera.config.EncoderUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter @Getter private Integer id;
    @Setter @Getter private String nombre;
    @Setter @Getter private String username;
    @Getter private String password;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Setter @Getter private List<Rol> roles;
    
    public void setPassword(String password) {
        this.password = EncoderUtils.passwordEncoder().encode(password);
    }
    
    
}
