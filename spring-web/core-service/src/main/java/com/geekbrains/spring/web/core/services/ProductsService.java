package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.core.entities.Product;
import com.geekbrains.spring.web.core.repositories.ProductsRepository;
import com.geekbrains.spring.web.core.repositories.specifications.ProductsSpecifications;
import com.geekbrains.spring.web.core.soap.products.ProductSoap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;

    public static final Function<Product, ProductSoap> functionEntityToSoap = product -> {
        ProductSoap productSoap = new ProductSoap();
        productSoap.setId(product.getId());
        productSoap.setTitle(product.getTitle());
        productSoap.setPrice(product.getPrice());
        productSoap.setCategoryTitle(product.getCategory().getTitle());
        return productSoap;
    };

    public Page<Product> findAll(Integer minPrice, Integer maxPrice, String partTitle, String categoryPartTitle, Integer page) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (partTitle != null) {
            spec = spec.and(ProductsSpecifications.titleLike(partTitle));
        }
        if (categoryPartTitle != null) {
            spec = spec.and(ProductsSpecifications.categoryLike(categoryPartTitle));
        }

        return productsRepository.findAll(spec, PageRequest.of(page - 1, 8));
    }

    @Cacheable("products")
    public Optional<Product> findById(Long id) {
        log.info("finding product by id: {}", id);
        return productsRepository.findById(id);
    }

    @CacheEvict("products")
    public void deleteById(Long id) {
        productsRepository.deleteById(id);
    }

    @CachePut(value = "products", key = "#product.title")
    public Product save(Product product) {
        return productsRepository.save(product);
    }

    //Тестовые методы
    @Cacheable(value = "products", key = "#product.title")
    public Product saveOrReturnCached(Product product) {
        log.info("creating product: {}", product);
        return productsRepository.save(product);
    }

    @CachePut(value = "products", key = "#product.title")
    public Product saveAndRefreshCache(Product product) {
        log.info("creating product: {}", product);
        return productsRepository.save(product);
    }
///////////////////////////////////////////////////////////////
    @Transactional
    public Product update(ProductDto productDto) {
        Product product = productsRepository.findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Невозможно обновить продукта, не надйен в базе, id: " + productDto.getId()));
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        return product;
    }
    public ProductSoap findProductById(Long id) {
        return productsRepository.findById(id).map(functionEntityToSoap).get();
    }

    public List<ProductSoap> findAllProducts() {
        return productsRepository.findAll().stream().map(functionEntityToSoap).collect(Collectors.toList());
    }
}
