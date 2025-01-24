package par.financiera.financiera.Domain.Dtos.RequestDto;

import lombok.Getter;
import lombok.Setter;
import par.financiera.financiera.Utils.GoalStatus;


import java.time.LocalDate;
@Getter
@Setter
public class GoalsRequestDto {

    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private GoalStatus status;
    private double currenAmount;
    private double goalAmount;
    private Long userId;
}
