package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.entities.Category;
import com.geekbrains.spring.web.repositories.CategoriesRepository;
import com.geekbrains.spring.web.soap.categories.CategorySoap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;

    public static final Function<Category, CategorySoap> functionEntityToSoap = category -> {
        CategorySoap categorySoap = new CategorySoap();
        categorySoap.setTitle(category.getTitle());
        category.getProducts().stream().map(ProductsService.functionEntityToSoap).forEach(product -> categorySoap.getProducts().add(product));
        return categorySoap;
    };


    public CategorySoap getByTitle(String title) {
        return categoriesRepository.findByTitle(title).map(functionEntityToSoap).get();
    }
}
