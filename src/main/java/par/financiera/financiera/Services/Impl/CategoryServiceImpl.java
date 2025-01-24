package par.financiera.financiera.Services.Impl;


import org.springframework.stereotype.Service;
import par.financiera.financiera.Domain.Categories;
import par.financiera.financiera.Domain.Dtos.RequestDto.CreateCategoryRequestDto;
import par.financiera.financiera.Domain.Dtos.ResponseDto.CreateCategoryResponseDto;
import par.financiera.financiera.Exceptions.ExceptionClass.ModelNotFounExcceptions;
import par.financiera.financiera.Repository.CategoryRepository;
import par.financiera.financiera.Services.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CreateCategoryResponseDto createCategory(CreateCategoryRequestDto requestDto) {

        if(this.categoryRepository.findByTitle(requestDto.getTitle()).isPresent()){
            throw  new ModelNotFounExcceptions("La categoria con ese titulo ya existe");

        }

        Categories category = Categories.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .build();

        this.categoryRepository.save(category);
        return convertToResponseDto(category);
    }

 //metodo para convertir una categoria en un Dto Response

    CreateCategoryResponseDto convertToResponseDto(Categories category) {
        return  CreateCategoryResponseDto.builder()
                .title(category.getTitle())
                .description(category.getDescription())
                .id(category.getId())
                .build();

    }

}
