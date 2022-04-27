package com.geekbrains.spring.web.core.service;

import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.core.entities.Category;
import com.geekbrains.spring.web.core.entities.Product;
import com.geekbrains.spring.web.core.services.ProductsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@Slf4j
@SpringBootTest
public class ProductsServiceTest {
    private static Product firstProduct;
    private static Product secondProduct;

    @Autowired
    private ProductsService productsService;

    @BeforeAll
    public static void createProduct(){
        Category category = new Category();
        category.setId(1L);
        category.setTitle("Vegetables");
        firstProduct = new Product();
        firstProduct.setTitle("Картоха");
        firstProduct.setPrice(new BigDecimal("2.00"));
        firstProduct.setCategory(category);

        secondProduct = new Product();
        secondProduct.setTitle("Помидора");
        secondProduct.setPrice(new BigDecimal("4.00"));
        secondProduct.setCategory(category);
    }

    @Test
    public void saveAndGet(){
        Product product1 = productsService.save(firstProduct);
        Product product2 = productsService.save(secondProduct);
        getAndPrint(product1.getId());
        getAndPrint(product2.getId());
        getAndPrint(product1.getId());
        getAndPrint(product2.getId());

    }

    @Test
    public void saveAndRefresh(){
        Product product3 = productsService.saveOrReturnCached(firstProduct);
        log.info("created product3: {}", product3);
        Product product4 = productsService.saveOrReturnCached(firstProduct);
        log.info("created product4: {}", product4);
        Product product5 = productsService.saveOrReturnCached(firstProduct);
        log.info("created product5: {}", product5);
        Product product6 = productsService.saveAndRefreshCache(firstProduct);
        log.info("created product6: {}", product6);
        Product product7 = productsService.saveAndRefreshCache(firstProduct);
        log.info("created product7: {}", product7);
    }

    private void getAndPrint(Long id){
        log.info("product found: {}", productsService.findById(id).orElseThrow(()-> new ResourceNotFoundException("product is not found")));
    }

}
