package com.app.service;

import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;
import com.app.model.dto.CategoryDto;
import com.app.model.dto.QuestionDto;
import com.app.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapperService modelMapperService;
    private QuestionService questionService;

    public CategoryService(CategoryRepository category, ModelMapperService modelMapperService, QuestionService questionService) {
        this.categoryRepository = category;
        this.modelMapperService = modelMapperService;
        this.questionService = questionService;
    }

    public void addCategory(CategoryDto category) {
        if (category == null) {
            throw new MyException(ExceptionCode.SERVICE, "ADD CATEGORY - CATEGORY IS NULL");
        }
        categoryRepository.add(modelMapperService.fromCategoryDtoToCategory(category));
    }

    public void updateCategory(CategoryDto category) {
        if (category == null) {
            throw new MyException(ExceptionCode.SERVICE, "UPDATE CATEGORY - CATEGORY IS NULL");
        }
        categoryRepository.update(modelMapperService.fromCategoryDtoToCategory(category));

    }

    public void deleteCategory(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "DELETE CATEGORY - ID IS NULL");
        }
        categoryRepository.delete(id);
    }

    public Optional<CategoryDto> findOneCategory(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "FIND ONE CATEGORY - ID IS NULL");
        }
        return categoryRepository.findById(id).map(p -> modelMapperService.fromCategoryToCategoryDto(p));
    }

    public List<CategoryDto> findAllCategory() {
        return categoryRepository
                .findAll()
                .stream()
                .map(p -> modelMapperService.fromCategoryToCategoryDto(p))
                .collect(Collectors.toList());
    }

    public Map<CategoryDto, Long> countCategories() {
        Map<CategoryDto, Long> map = questionService.findAllQuestion()
                .stream()
                .map(QuestionDto::getCategory)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        findAllCategory()
                .stream()
                .filter(c -> c.getId() != 1)
                .filter(c -> !map.containsKey(c))
                .forEach(c -> map.put(c, 0L));

        return map;
    }

    public void addStandardCategory() {
        List<CategoryDto> categoryList = findAllCategory();
        Optional<CategoryDto> categoryLack = categoryList.stream().filter(s -> s.getName().equals("WSZYSTKO")).findFirst();
        if (!categoryLack.isPresent()) {
            addCategory(new CategoryDto().builder().name("WSZYSTKO").build());
        }
    }
}
