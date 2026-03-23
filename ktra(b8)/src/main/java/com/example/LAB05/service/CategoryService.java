package com.example.LAB05.service;

import com.example.LAB05.model.Category;
import com.example.LAB05.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAll(){
        return categoryRepository.findAll();
    }

    public Category getById(Integer id){
        return categoryRepository.findById(Objects.requireNonNull(id)).orElse(null);
    }

}