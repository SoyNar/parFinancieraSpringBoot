package par.financiera.financiera.Services.Impl;

import org.springframework.stereotype.Service;
import par.financiera.financiera.Domain.Dtos.RequestDto.GoalsRequestDto;
import par.financiera.financiera.Domain.Dtos.ResponseDto.GoalResponseDto;
import par.financiera.financiera.Domain.Goals;
import par.financiera.financiera.Domain.User;
import par.financiera.financiera.Exceptions.ExceptionClass.InvalidRequestException;
import par.financiera.financiera.Exceptions.ExceptionClass.UserNotFoundException;
import par.financiera.financiera.Repository.IGoalsRepository;
import par.financiera.financiera.Repository.UserRepository;
import par.financiera.financiera.Services.IGoalsService;
import par.financiera.financiera.Utils.GoalStatus;

import java.time.LocalDate;

@Service
public class GoalsServiceImpl implements IGoalsService {
    private final IGoalsRepository goalsRepository;
    private final UserRepository userRepository;

    public GoalsServiceImpl(IGoalsRepository goalsRepository, UserRepository userRepository) {
        this.goalsRepository = goalsRepository;
        this.userRepository = userRepository;
    }


    @Override
    public GoalResponseDto createGoals(GoalsRequestDto requestDto) {


        //validar que el reques no este vacio
        if (requestDto == null) {
            throw new InvalidRequestException("el request no puede estar vacio");
        }

        //validar que los campos obligatorios no esten vacios
        validateRequiredFields(requestDto);

        //buscar usuario por id
        User user = this.userRepository.findById(requestDto.getUserId()).orElseThrow(()
                -> new UserNotFoundException("usuario no  encontrado "));

        //validar que el salod amount no sea menor a cero
        validateAmounts(requestDto.getCurrenAmount(),requestDto.getGoalAmount());

        //validar si la fecha de inicio es despues de la fecha de fin
        validateDate(requestDto.getStartDate(),requestDto.getEndDate());

        //cambiar el estado a completado
       GoalStatus status = determineGoalStatus(requestDto.getCurrenAmount(), requestDto.getGoalAmount());

        //crear la meta

        Goals goaslCreate = Goals.builder()
                .title(requestDto.getTitle())
                .status(GoalStatus.IN_PROGRESS)
                .description(requestDto.getDescription())
                .currentAmount(requestDto.getCurrenAmount())
                .goalAmount(requestDto.getGoalAmount())
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .user(user)
                .build();

        //guardar la meta
        this.goalsRepository.save(goaslCreate);

        return coverToGoalResponseDto(goaslCreate);
    }

    GoalResponseDto coverToGoalResponseDto(Goals goal) {
        return GoalResponseDto.builder()
                .title(goal.getTitle())
                .description(goal.getDescription())
                .currenAmount(goal.getCurrentAmount())
                .goalAmount(goal.getGoalAmount())
                .startDate(goal.getStartDate())
                .status(goal.getStatus())
                .endDate(goal.getEndDate())
                .userId(goal.getUser().getId())
                .build();

    }

    public void validateRequiredFields(GoalsRequestDto requestDto) {

        if (requestDto.getTitle() == null || requestDto.getTitle().trim().isEmpty()) {
            throw new InvalidRequestException(" no puede estar vacio");
        }

        if (requestDto.getStartDate() == null || requestDto.getEndDate() == null) {
            throw new InvalidRequestException("Las fechas no pueden estar vacias");
        }

        if (requestDto.getUserId() == null) {
            throw new InvalidRequestException(" El id del usuario no puede estar vacio");
        }


    }

    public void validateAmounts(double currentAmount, double goalAmount){
        if(currentAmount > goalAmount){
            throw new InvalidRequestException(" la cantidad actual no puede ser mayor que la cantidad final");
        }

        if(currentAmount < 0){
            throw new InvalidRequestException("la cantidad actual no puede ser menor a cero");
        }

        if(goalAmount <= 0){
            throw new InvalidRequestException("la cantidad actual no puede ser menor a cero");
        }


    }

    private GoalStatus determineGoalStatus(double currentAmount, double goalAmount) {
        if (currentAmount >= goalAmount) {
            return GoalStatus.COMPLETED;
        }
        return GoalStatus.IN_PROGRESS;
    }

    public void validateDate(LocalDate startDate, LocalDate endDate){
        LocalDate today = LocalDate.now();

        if(startDate.isBefore(today)){
            throw new InvalidRequestException("la fecha de inicio no puede ser anterior a hoy");
        }
        if(startDate.isAfter(endDate)){
            throw  new InvalidRequestException(" la fecha de inicio no puede ser despues dde la final");
        }
    }

}
