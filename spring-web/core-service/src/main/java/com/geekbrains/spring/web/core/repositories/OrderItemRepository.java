package com.geekbrains.spring.web.core.repositories;

import com.geekbrains.spring.web.core.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("select it from OrderItem it where it.createdAt between ?1 and ?2")
    List<OrderItem> findAllByDate(LocalDateTime createdAt, LocalDateTime finishedAt);
}
