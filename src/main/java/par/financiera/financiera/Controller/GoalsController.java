package par.financiera.financiera.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import par.financiera.financiera.Domain.Dtos.ResponseDto.GoalResponseDto;
import par.financiera.financiera.Services.IGoalsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/goals")
public class GoalsController {

private  final IGoalsService goalsService;

public GoalsController(IGoalsService goalsService) {
this.goalsService = goalsService;
}
   @GetMapping("/goals")
    public ResponseEntity<?> getAllGoals(){
        List<GoalResponseDto> allgoals = this.goalsService.getAllGoals();
        return ResponseEntity.status(HttpStatus
                .OK).body(allgoals);
    }
}
