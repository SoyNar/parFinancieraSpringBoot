package par.financiera.financiera.Services.Impl;


import par.financiera.financiera.Domain.Dtos.RequestDto.RegisterCashFlowRequestDto;
import par.financiera.financiera.Domain.Dtos.ResponseDto.GetExpensesResponse;
import par.financiera.financiera.Domain.Dtos.ResponseDto.RegisterCashFlowResponseDto;

import java.util.List;

public interface ICashFlowService {
    //crear gasto
    RegisterCashFlowResponseDto registerIncome(RegisterCashFlowRequestDto requestDto);
    RegisterCashFlowResponseDto registerExpense(RegisterCashFlowRequestDto requestDto);
    List<GetExpensesResponse> getExpenses (Long userId);
}
