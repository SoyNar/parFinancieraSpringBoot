package par.financiera.financiera.Domain.Dtos.ResponseDto;


import lombok.*;
import par.financiera.financiera.Utils.TypeCash;

import java.time.LocalDate;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCashFlowResponseDto {

    private String title;
    private double amount;
    private TypeCash type;
    private LocalDate date;
}
