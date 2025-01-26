package par.financiera.financiera.Services.Impl;


import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import par.financiera.financiera.Domain.Dtos.RequestDto.RegisterUserRequestDto;
import par.financiera.financiera.Domain.Dtos.ResponseDto.RegisterUserResponseDto;
import par.financiera.financiera.Domain.User;
import par.financiera.financiera.Exceptions.ExceptionClass.InvalidRequestException;
import par.financiera.financiera.Exceptions.ExceptionClass.UserNotFoundException;
import par.financiera.financiera.Repository.UserRepository;
import par.financiera.financiera.Services.IUserService;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    @Override
    public RegisterUserResponseDto registerUser(RegisterUserRequestDto requestDto) {
            if(this.userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
                throw new UserNotFoundException("usuario ya existe");
            }

            //validaciones del request
        validateRequest(requestDto);

            //agregar al usuario a la base de datos
        User user = User.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .build();
            this.userRepository.save(user);
        return RegisterUserResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();

    }


    private void validateRequest(RegisterUserRequestDto requestDto) {
        if (requestDto == null) {
            throw new InvalidRequestException("La solicitud no puede ser nula");
        }

        if (StringUtils.isBlank(requestDto.getEmail())) {
            throw new InvalidRequestException("El email es requerido");
        }

        if (StringUtils.isBlank(requestDto.getPassword())) {
            throw new InvalidRequestException("La contraseña es requerida");
        }

        if (StringUtils.isBlank(requestDto.getName())) {
            throw new InvalidRequestException("El nombre es requerido");
        }


        if (requestDto.getPassword().length() < 8) {
            throw new InvalidRequestException("La contraseña debe tener al menos 8 caracteres");
        }
    }
}
