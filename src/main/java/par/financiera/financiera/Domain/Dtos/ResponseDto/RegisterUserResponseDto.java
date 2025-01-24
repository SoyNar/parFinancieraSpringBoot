package par.financiera.financiera.Domain.Dtos.ResponseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterUserResponseDto {
    private String email;
    private String name;

}
