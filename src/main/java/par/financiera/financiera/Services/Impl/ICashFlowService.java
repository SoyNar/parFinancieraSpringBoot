package par.financiera.financiera.Services.Impl;


import par.financiera.financiera.Domain.Dtos.RequestDto.RegisterCashFlowRequestDto;
import par.financiera.financiera.Domain.Dtos.ResponseDto.GetCashResponse;
import par.financiera.financiera.Domain.Dtos.ResponseDto.RegisterCashFlowResponseDto;

import java.util.List;

public interface ICashFlowService {
    //crear gasto
    RegisterCashFlowResponseDto registerIncome(RegisterCashFlowRequestDto requestDto);
    RegisterCashFlowResponseDto registerExpense(RegisterCashFlowRequestDto requestDto);
    List<GetCashResponse> getExpenses (Long userId, String month);
    List<GetCashResponse> getIncome(Long userId, String month);

}
