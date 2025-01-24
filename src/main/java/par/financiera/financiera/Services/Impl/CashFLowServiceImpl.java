package par.financiera.financiera.Services.Impl;


import org.springframework.stereotype.Service;
import par.financiera.financiera.Domain.CashFlow;
import par.financiera.financiera.Domain.Categories;
import par.financiera.financiera.Domain.Dtos.RequestDto.RegisterCashFlowRequestDto;
import par.financiera.financiera.Domain.Dtos.ResponseDto.GetExpensesResponse;
import par.financiera.financiera.Domain.Dtos.ResponseDto.RegisterCashFlowResponseDto;
import par.financiera.financiera.Domain.User;
import par.financiera.financiera.Exceptions.ExceptionClass.InvalidRequestException;
import par.financiera.financiera.Exceptions.ExceptionClass.UserNotFoundException;
import par.financiera.financiera.Repository.CashFlowRepository;
import par.financiera.financiera.Repository.CategoryRepository;
import par.financiera.financiera.Repository.UserRepository;
import par.financiera.financiera.Utils.TypeCash;

import java.time.LocalDate;
import java.util.List;

@Service
public class CashFLowServiceImpl  implements ICashFlowService {

    private final UserRepository userRepository;
    private final CashFlowRepository cashFlowRepository;
    private final CategoryRepository categoryRepository;

    public CashFLowServiceImpl(UserRepository userRepository, CashFlowRepository cashFlowRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.cashFlowRepository = cashFlowRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public RegisterCashFlowResponseDto registerIncome(RegisterCashFlowRequestDto requestDto) {
        //validar que el request no este vacio
        if (requestDto.getTitle() == null || requestDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("titulo no puede estar vacio");
        }

        //validar que el usuario existe
        User userExist = this.userRepository.findById(requestDto.getUserId()).orElseThrow(()
                -> new UserNotFoundException("Usuario no Encontrado"));

        //validar que la categoria existe
        Categories categories = this.categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(()
                -> new InvalidRequestException("categoria no encontrada"));

        //validar que la cantidad no puede ser menor a cero
        if (requestDto.getAmount() <= 0) {
            throw new InvalidRequestException("amount la cantidad no puede ser menor o igual a cero");
        }

        //sumar la nueva cantidad a la cantidad actual
        //obtener el valor total de el balance del usuario
        double currentTotalIncome = userExist.getBalance() != null ? userExist.getBalance() : 0.0;

        //agregar la nueva cantidad
        double newBalance = currentTotalIncome + requestDto.getAmount();

        // Update the user's total income
        userExist.setBalance(newBalance);

        //convertir de entidad a Dto
        CashFlow cashFlow = CashFlow.builder()
                .type(TypeCash.INCOME)
                .date(LocalDate.now())
                .user(userExist)
                .amount(requestDto.getAmount())
                .title(requestDto.getTitle())  // Agregado el título que faltaba
                .build();

        //guardar a traves del repositorio
        this.cashFlowRepository.save(cashFlow);

        //retornar un responseDto
        return covertToDtoResponse(cashFlow);
    }

    @Override
    public RegisterCashFlowResponseDto registerExpense(RegisterCashFlowRequestDto requestDto) {


        //validar el request

        if(requestDto.getTitle() == null) {
            throw  new InvalidRequestException("el titulo no puede ser vacio");
        }


        //validar que el usuario existe
        User userExist = this.userRepository.findById(requestDto.getUserId()).orElseThrow(()
                -> new UserNotFoundException("Usuario no Encontrado"));

        //validar que la categoria existe
        Categories categories = this.categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(()
                -> new InvalidRequestException("categoria no encontrada"));

        //validar que la cantidad no puede ser menor a cero
        if (requestDto.getAmount() <= 0) {
            throw new InvalidRequestException("amount la cantidad no puede ser menor o igual a cero");
        }

        //sumar la nueva cantidad a la cantidad actual
        //obtener el valor total de el balance del usuario
        double currentTotalIncome = userExist.getBalance() != null ? userExist.getBalance() : 0.0;

        //restar la nueva cantidad
        double newBalance = currentTotalIncome - requestDto.getAmount();

        // Update the user's total income
        userExist.setBalance(newBalance);

        //convertir de entidad a Dto
        CashFlow cashFlow = CashFlow.builder()
                .type(TypeCash.EXPENSES)
                .user(userExist)
                .date(LocalDate.now())
                .amount(requestDto.getAmount())
                .title(requestDto.getTitle())  // Agregado el título que faltaba
                .build();

        //guardar a traves del repositorio
        this.cashFlowRepository.save(cashFlow);

        //retornar un responseDto
        return covertToDtoResponse(cashFlow);
    }

    @Override
    public List<GetExpensesResponse> getExpenses(Long userId) {


        //validar si el usuario existe
        this.userRepository.findById(userId).orElseThrow(()
                -> new UserNotFoundException("Usuario no encontrado"));


             //buscar gastos por id de usuario
        List<CashFlow> expenses = cashFlowRepository.findByUserIdAndType(userId, TypeCash.EXPENSES);


        //buscar expendes del usuario
        if(expenses.isEmpty()){
            throw  new UserNotFoundException("El usuario no tiene gastos registrados");
        }
        return expenses.stream()
                .map(expense -> GetExpensesResponse.builder()
                        .title(expense.getTitle())
                        .categoryId(expense.getCategories().getId())
                        .date(expense.getDate())
                        .amount(expense.getAmount())
                        .userId(expense.getUser().getId())
                        .build()

                )
                .toList();
    }

    RegisterCashFlowResponseDto covertToDtoResponse(CashFlow cashFlow){
         return  RegisterCashFlowResponseDto.builder()
                .date(cashFlow.getDate())
                .title(cashFlow.getTitle())
                .amount(cashFlow.getAmount())
                .type(cashFlow.getType())
                .build();
    }
}
