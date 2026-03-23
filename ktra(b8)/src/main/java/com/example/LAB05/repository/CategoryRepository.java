package com.example.LAB05.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.LAB05.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

}