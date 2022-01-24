package com.geekbrains.spring.web.repositories;

import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
}
