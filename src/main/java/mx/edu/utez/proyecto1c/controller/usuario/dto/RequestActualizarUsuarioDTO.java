package mx.edu.utez.proyecto1c.controller.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestActualizarUsuarioDTO {

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 4, max = 20, message = "El username debe ser entre 4 y 20 caracteres")
    private String username;

    private String descripcion;
}