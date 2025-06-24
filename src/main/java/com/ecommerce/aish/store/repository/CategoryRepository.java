package com.ecommerce.aish.store.repository;

import com.ecommerce.aish.store.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}