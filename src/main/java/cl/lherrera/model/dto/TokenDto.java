package cl.lherrera.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class TokenDto {
    private Integer status;
    private String mensaje;
    private String token;
}
