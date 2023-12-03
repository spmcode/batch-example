package com.sid.batch.repo;

import com.sid.batch.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo  extends JpaRepository<Product,Integer> {
}
