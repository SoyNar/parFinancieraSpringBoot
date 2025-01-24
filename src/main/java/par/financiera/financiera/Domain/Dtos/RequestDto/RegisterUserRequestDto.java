package par.financiera.financiera.Domain.Dtos.RequestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterUserRequestDto {
    @NotBlank(message = "el nombre es obligatorio")
    private String name;
    @NotBlank(message = "el correo es obligatorio")

    private String email;
    @NotBlank(message = "la contraseña es obligatoria")

    private String password;
}
