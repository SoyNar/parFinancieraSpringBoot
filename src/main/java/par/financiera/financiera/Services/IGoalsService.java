package par.financiera.financiera.Services;

import par.financiera.financiera.Domain.Dtos.RequestDto.GoalsRequestDto;
import par.financiera.financiera.Domain.Dtos.ResponseDto.GoalResponseDto;

public interface IGoalsService {
    GoalResponseDto createGoals(GoalsRequestDto requestDto);
}
