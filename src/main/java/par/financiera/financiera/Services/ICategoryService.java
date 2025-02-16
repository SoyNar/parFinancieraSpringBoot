package par.financiera.financiera.Services;


import par.financiera.financiera.Domain.Dtos.RequestDto.CreateCategoryRequestDto;
import par.financiera.financiera.Domain.Dtos.ResponseDto.CreateCategoryResponseDto;

import java.util.List;

public interface ICategoryService {

    CreateCategoryResponseDto createCategory (CreateCategoryRequestDto requestDto);
    List<CreateCategoryResponseDto> getCategory();
}
