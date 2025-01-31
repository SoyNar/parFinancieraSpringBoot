package par.financiera.financiera;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import par.financiera.financiera.Domain.Categories;
import par.financiera.financiera.Domain.Dtos.RequestDto.RegisterCashFlowRequestDto;
import par.financiera.financiera.Domain.Dtos.ResponseDto.RegisterCashFlowResponseDto;
import par.financiera.financiera.Domain.User;
import par.financiera.financiera.Exceptions.ExceptionClass.InvalidRequestException;
import par.financiera.financiera.Exceptions.ExceptionClass.UserNotFoundException;
import par.financiera.financiera.Repository.CategoryRepository;
import par.financiera.financiera.Repository.UserRepository;
import par.financiera.financiera.Services.Impl.ICashFlowService;
import par.financiera.financiera.Utils.TypeCash;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CashFLowServiceIntegrationTest {


    @Autowired
    private ICashFlowService cashFlowService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testRegisterIncome_Success() {
        // Crear un usuario y una categoría para la prueba
        User user = new User();
        user.setName("Juan");
        user.setEmail("juan@example.com");
        user.setPassword("madrid");
        user.setBalance(0.0);
        userRepository.save(user);

        Categories category = new Categories();
        category.setTitle("Salario");
        categoryRepository.save(category);

        // Crear el DTO de solicitud
        RegisterCashFlowRequestDto requestDto = new RegisterCashFlowRequestDto();
        requestDto.setUserId(user.getId());
        requestDto.setCategoryId(category.getId());
        requestDto.setTitle("Salario Mensual");
        requestDto.setAmount(1500.0);

        // Ejecutar el método
        RegisterCashFlowResponseDto responseDto = cashFlowService.registerIncome(requestDto);

        // Verificar el resultado
        assertNotNull(responseDto);
        assertEquals(requestDto.getTitle(), responseDto.getTitle());
        assertEquals(requestDto.getAmount(), responseDto.getAmount());
        assertEquals(TypeCash.INCOME, responseDto.getType());
        assertEquals(LocalDate.now(), responseDto.getDate());

        // Verificar que el balance del usuario se actualizó correctamente
        User updatedUser = userRepository.findById(user.getId()).orElseThrow();
        assertEquals(1500.0, updatedUser.getBalance());
    }

    @Test
    public void testRegisterIncome_UserNotFound() {
        // Crear el DTO de solicitud con un ID de usuario que no existe
        RegisterCashFlowRequestDto requestDto = new RegisterCashFlowRequestDto();
        requestDto.setUserId(999L); // ID que no existe
        requestDto.setCategoryId(1L);
        requestDto.setTitle("Salario Mensual");
        requestDto.setAmount(1500.0);

        // Verificar que se lanza la excepción
        assertThrows(UserNotFoundException.class, () -> {
            cashFlowService.registerIncome(requestDto);
        });
    }

    @Test
    public void testRegisterIncome_CategoryNotFound() {
        // Crear un usuario para la prueba
        User user = new User();
        user.setName("Juan");
        user.setEmail("juan@example.com");
        user.setPassword("12345");
        user.setBalance(0.0);
        userRepository.save(user);

        // Crear el DTO de solicitud con un ID de categoría que no existe
        RegisterCashFlowRequestDto requestDto = new RegisterCashFlowRequestDto();
        requestDto.setUserId(user.getId());
        requestDto.setCategoryId(999L); // ID que no existe
        requestDto.setTitle("Salario Mensual");
        requestDto.setAmount(1500.0);

        // Verificar que se lanza la excepción
        assertThrows(InvalidRequestException.class, () -> {
            cashFlowService.registerIncome(requestDto);
        });
    }
}
