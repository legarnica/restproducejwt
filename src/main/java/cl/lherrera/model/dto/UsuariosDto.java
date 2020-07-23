package cl.lherrera.model.dto;

import java.util.List;

import cl.lherrera.model.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class UsuariosDto {
    private Integer status;
    private List<Usuario> data;
}
