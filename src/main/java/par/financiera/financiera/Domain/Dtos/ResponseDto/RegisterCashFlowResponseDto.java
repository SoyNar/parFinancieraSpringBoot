package par.financiera.financiera.Domain.Dtos.ResponseDto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import par.financiera.financiera.Utils.TypeCash;

import java.time.LocalDate;

@Builder
@Setter
@Getter
public class RegisterCashFlowResponseDto {

    private String title;
    private double amount;
    private TypeCash type;
    private LocalDate date;
}
