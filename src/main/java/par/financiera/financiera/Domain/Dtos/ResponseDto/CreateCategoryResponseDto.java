package par.financiera.financiera.Domain.Dtos.ResponseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateCategoryResponseDto {

    private String title;
    private Long id;
    private String description;

}
