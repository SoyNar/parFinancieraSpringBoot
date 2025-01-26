package par.financiera.financiera.Domain.Dtos.ResponseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class GetCashResponse {

    private String title;
    private double amount;
    private Long userId;
    private Long categoryId;
    private LocalDate date;
}
