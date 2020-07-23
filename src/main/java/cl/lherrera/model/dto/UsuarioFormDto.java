package cl.lherrera.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class UsuarioFormDto {
    private String nombre;
    private String username;
    private String password;
}
