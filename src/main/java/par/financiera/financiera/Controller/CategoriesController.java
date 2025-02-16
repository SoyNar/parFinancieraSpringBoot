package par.financiera.financiera.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import par.financiera.financiera.Domain.Dtos.ResponseDto.CreateCategoryResponseDto;
import par.financiera.financiera.Services.ICategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesController {

    private final ICategoryService categoryService;

    @Autowired
    public CategoriesController (ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CreateCategoryResponseDto>> getAllCategories(){
        List<CreateCategoryResponseDto> responseDto = this.categoryService.getCategory();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
