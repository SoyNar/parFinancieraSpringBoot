package par.financiera.financiera.Services;


import par.financiera.financiera.Domain.Dtos.RequestDto.CreateCategoryRequestDto;
import par.financiera.financiera.Domain.Dtos.ResponseDto.CreateCategoryResponseDto;

public interface ICategoryService {

    CreateCategoryResponseDto createCategory (CreateCategoryRequestDto requestDto);
}
