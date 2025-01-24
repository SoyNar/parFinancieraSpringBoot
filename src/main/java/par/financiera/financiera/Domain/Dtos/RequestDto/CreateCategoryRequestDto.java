package par.financiera.financiera.Domain.Dtos.RequestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CreateCategoryRequestDto {
    private String title;
    private String description;
}
