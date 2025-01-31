package par.financiera.financiera.Services.Impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import par.financiera.financiera.Domain.CashFlow;
import par.financiera.financiera.Domain.Categories;
import par.financiera.financiera.Domain.Dtos.RequestDto.RegisterCashFlowRequestDto;
import par.financiera.financiera.Domain.Dtos.ResponseDto.GetCashResponse;
import par.financiera.financiera.Domain.Dtos.ResponseDto.RegisterCashFlowResponseDto;
import par.financiera.financiera.Domain.User;
import par.financiera.financiera.Exceptions.ExceptionClass.CashFlowNotFound;
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


    @Transactional
    @Override
    public RegisterCashFlowResponseDto registerIncome(RegisterCashFlowRequestDto requestDto) {
        //validar que el request no este vacio

        validateRegisterCashFlowRequest(requestDto);

        //validar que el usuario existe
        User userExist = this.userRepository.findById(requestDto.getUserId()).orElseThrow(()
                -> new UserNotFoundException("Usuario no Encontrado"));

        //validar que la categoria existe
        Categories categories = this.categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(()
                -> new InvalidRequestException("categoria no encontrada"));

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
                .categories(categories)
                .user(userExist)
                .amount(requestDto.getAmount())
                .title(requestDto.getTitle())
                .build();

        //guardar a traves del repositorio
        this.cashFlowRepository.save(cashFlow);

        //retornar un responseDto
        return covertToDtoResponse(cashFlow);
    }

    @Transactional
    @Override
    public RegisterCashFlowResponseDto registerExpense(RegisterCashFlowRequestDto requestDto) {

//validaciones
        validateRegisterCashFlowRequest(requestDto);


        //validar que el usuario existe
        User userExist = this.userRepository.findById(requestDto.getUserId()).orElseThrow(()
                -> new UserNotFoundException("Usuario no Encontrado"));

        //validar que la categoria existe
        Categories categories = this.categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(()
                -> new InvalidRequestException("categoria no encontrada"));

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
                .categories(categories)
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

    @Transactional
    @Override
    public List<GetCashResponse> getExpenses(Long userId, String month) {


        //validar si el usuario existe
        this.userRepository.findById(userId).orElseThrow(()
                -> new UserNotFoundException("Usuario no encontrado"));


             //buscar gastos por id de usuario
        List<CashFlow> expenses = cashFlowRepository.findByUserIdAndTypeAndMonth(userId, TypeCash.EXPENSES,month);


        //buscar expendes del usuario
        if(expenses.isEmpty()){
            throw  new CashFlowNotFound("El usuario no tiene gastos registrados");
        }
        return expenses.stream()
                .map(expense -> GetCashResponse.builder()

                        .title(expense.getTitle())
                        .categoryId(expense.getCategories().getId())
                        .date(expense.getDate())
                        .amount(expense.getAmount())
                        .userId(expense.getUser().getId())
                        .build()

                )
                .toList();
    }

    @Transactional
    @Override
    public List<GetCashResponse> getIncome(Long userId,String month) {
        //validar si el usuario existe
        this.userRepository.findById(userId).orElseThrow(()
                -> new UserNotFoundException("Usuario no encontrado"));


        //buscar gastos por id de usuario
        List<CashFlow> expenses = cashFlowRepository.findByUserIdAndTypeAndMonth(userId, TypeCash.INCOME,month);


        //buscar expendes del usuario
        if(expenses.isEmpty()){
            throw  new UserNotFoundException("El usuario no tiene Ingresos registrados");
        }
        return expenses.stream()
                .map(expense -> GetCashResponse.builder()

                        .title(expense.getTitle())
                        .categoryId(expense.getCategories() != null ? expense.getCategories().getId() : null)  // Manejo de null
                        .date(expense.getDate())
                        .amount(expense.getAmount())
                        .userId(expense.getUser() != null ? expense.getUser().getId() : null)  // Manejo de null
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

    private void validateRegisterCashFlowRequest(RegisterCashFlowRequestDto requestDto) {
        // Validar que el request no esté vacío
        if (requestDto == null) {
            throw new InvalidRequestException("El request no puede ser nulo");
        }

        // Validar que la categoría no sea nula
        if (requestDto.getCategoryId() == null) {
            throw new InvalidRequestException("La categoría no puede estar vacía");
        }

        // Validar que el título no sea nulo o vacío
        if (requestDto.getTitle() == null || requestDto.getTitle().isEmpty()) {
            throw new InvalidRequestException("El título no puede estar vacío");
        }

        // Validar que la cantidad no sea menor o igual a cero
        if (requestDto.getAmount() <= 0) {
            throw new InvalidRequestException("La cantidad no puede ser menor o igual a cero");
        }
    }
}
