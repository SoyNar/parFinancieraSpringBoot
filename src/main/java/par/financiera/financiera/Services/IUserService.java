package par.financiera.financiera.Services;


import par.financiera.financiera.Domain.Dtos.RequestDto.RegisterUserRequestDto;
import par.financiera.financiera.Domain.Dtos.ResponseDto.RegisterUserResponseDto;

public interface IUserService {
    RegisterUserResponseDto registerUser (RegisterUserRequestDto requesDto);
}
