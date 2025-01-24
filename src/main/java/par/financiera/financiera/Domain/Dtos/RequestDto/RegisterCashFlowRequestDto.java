package par.financiera.financiera.Domain.Dtos.RequestDto;

import lombok.Getter;
import lombok.Setter;
import par.financiera.financiera.Utils.TypeCash;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterCashFlowRequestDto {
    private String title;
    private Long categoryId;
    private double amount;
    private LocalDate date;
    private Long userId;
    private TypeCash type;

}
