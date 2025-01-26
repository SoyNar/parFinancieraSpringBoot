package par.financiera.financiera.Controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import par.financiera.financiera.Domain.Dtos.RequestDto.CreateCategoryRequestDto;
import par.financiera.financiera.Domain.Dtos.RequestDto.GoalsRequestDto;
import par.financiera.financiera.Domain.Dtos.RequestDto.RegisterCashFlowRequestDto;
import par.financiera.financiera.Domain.Dtos.RequestDto.RegisterUserRequestDto;
import par.financiera.financiera.Domain.Dtos.ResponseDto.*;
import par.financiera.financiera.Services.ICategoryService;
import par.financiera.financiera.Services.IGoalsService;
import par.financiera.financiera.Services.IUserService;
import par.financiera.financiera.Services.Impl.ICashFlowService;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final IUserService userService;

    private  final ICashFlowService cashFlowService;
    private final ICategoryService  categoryService;
    private  final IGoalsService goalsService;


    @Autowired
    public UserController(IUserService userService, ICashFlowService cashFlowService, ICategoryService categoryService, IGoalsService goalsService) {
        this.userService = userService;
        this.cashFlowService = cashFlowService;
        this.categoryService = categoryService;
        this.goalsService = goalsService;
    }
   @Operation(
           summary = "Crear un usuario",
           description = "aqui puedes crear un usuario",
           tags = {"users,register"}
   )
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDto> registerUser (@RequestBody RegisterUserRequestDto requestDto){
        RegisterUserResponseDto registered = this.userService.registerUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    //metodo para que un usuario pueda registrar sus ingresos

    @Operation(
            summary = "registrar ingresos",
            description = "aqui puedes registrar ingresos de un usuario",
            tags = {"income,register"}
    )

    @PostMapping("/ingreso")
    public ResponseEntity<RegisterCashFlowResponseDto> registerIncome(@RequestBody RegisterCashFlowRequestDto requestDto){
        RegisterCashFlowResponseDto registerIncome = this.cashFlowService.registerIncome(requestDto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(registerIncome);
    }

    //metodo para crear una categoria

    @Operation(
            summary = "crear categoria",
            description = "aqui puedes crear una categoria",
            tags = {"category,create"}
    )

    @PostMapping("/category")
    public  ResponseEntity<CreateCategoryResponseDto> createCategory ( @RequestBody CreateCategoryRequestDto requestDto){
       CreateCategoryResponseDto createCategory = this.categoryService.createCategory(requestDto);

       return  ResponseEntity.status(HttpStatus.CREATED).body(createCategory);
    }

    //metodo para registrar un gasto
    @Operation(
            summary = "Registrar un gasto",
            description = "Aqui puedes registrar tus gastos",
            tags = {"register,expenses"}
    )
    @PostMapping("/expense")
    public ResponseEntity<RegisterCashFlowResponseDto> registerExpense(@RequestBody RegisterCashFlowRequestDto requestDto){
        RegisterCashFlowResponseDto responseDto = this.cashFlowService.registerExpense(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    //metodo para crear una meta

    @Operation(
            summary = "Registrar metas",
            description = "Aqui puedes registrar tus metas",
            tags = {"Goals,user"}
    )
    @PostMapping("/goals")
    public  ResponseEntity<GoalResponseDto> createGoal(@RequestBody GoalsRequestDto requestDto){
        GoalResponseDto goalSave = this.goalsService.createGoals(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(goalSave);
    }

    @Operation(
            summary = "Obtener gastos por usuario",
            description = "Aqui puedes obtener los gastos de un usuario",
            tags = {"Get,expenses"}
    )
    @GetMapping("/expenses/{id}/{month}")
    public ResponseEntity<List<GetCashResponse>> getExpensesByUser(@PathVariable Long id, @PathVariable String month){
        List<GetCashResponse> expensesResponse = this.cashFlowService.getExpenses(id, month);
        return ResponseEntity.status(HttpStatus.OK).body(expensesResponse);
    }

    @Operation(
            summary = "Obtener Ingresos por usuario",
            description = "Aqui puedes obtener los Ingresos de un usuario",
            tags = {"Get,income"}
    )
    @GetMapping("/income/{id}/{month}")
    public ResponseEntity<List<GetCashResponse>> getIncomeByUserAndMonth(@PathVariable Long id, @PathVariable String month){
        List<GetCashResponse> getIncome = this.cashFlowService.getIncome(id,month);
         return ResponseEntity.status(HttpStatus.OK).body(getIncome);

    }
}
