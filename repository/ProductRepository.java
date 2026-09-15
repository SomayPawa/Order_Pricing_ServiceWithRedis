package com.slp.order_pricing_service.repository;

import com.slp.order_pricing_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}