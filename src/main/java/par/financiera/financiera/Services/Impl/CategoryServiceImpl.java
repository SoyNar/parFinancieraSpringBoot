package par.financiera.financiera.Services.Impl;


import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import par.financiera.financiera.Domain.Categories;
import par.financiera.financiera.Domain.Dtos.RequestDto.CreateCategoryRequestDto;
import par.financiera.financiera.Domain.Dtos.ResponseDto.CreateCategoryResponseDto;
import par.financiera.financiera.Exceptions.ExceptionClass.BadRequestExceptions;
import par.financiera.financiera.Exceptions.ExceptionClass.ModelNotFounExcceptions;
import par.financiera.financiera.Repository.CategoryRepository;
import par.financiera.financiera.Services.ICategoryService;

import java.util.List;


@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Transactional
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

    /**
     * metodo para obtener las categorias creadas
     * @param
     * */
    @Transactional
    @Override
    public List<CreateCategoryResponseDto> getCategory() {



       List<Categories> allCategories = this.categoryRepository.findAll();


        return allCategories.stream()
                .map( response -> CreateCategoryResponseDto.builder()
                        .id(response.getId())
                        .title(response.getTitle())
                        .description(response.getDescription())
                        .build())
                .toList();
    }

}
