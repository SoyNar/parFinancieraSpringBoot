package par.financiera.financiera.Services;

import par.financiera.financiera.Domain.Dtos.RequestDto.GoalsRequestDto;
import par.financiera.financiera.Domain.Dtos.ResponseDto.GoalResponseDto;

import java.util.List;

public interface IGoalsService {
    GoalResponseDto createGoals(GoalsRequestDto requestDto);
    List<GoalResponseDto> getAllGoals();
    List<GoalResponseDto> getGoalsById(Long id, Long idGoal);
}
