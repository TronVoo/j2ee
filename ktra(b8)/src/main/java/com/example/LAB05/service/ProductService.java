package com.example.LAB05.service;

import com.example.LAB05.model.Product;
import com.example.LAB05.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public Page<Product> searchProducts(String keyword, Integer categoryId, int page, int size, String sortOption){
        Sort sort = Sort.unsorted();

        if("priceAsc".equalsIgnoreCase(sortOption)){
            sort = Sort.by("price").ascending();
        }else if("priceDesc".equalsIgnoreCase(sortOption)){
            sort = Sort.by("price").descending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Product> specification = (root, query, cb) -> cb.conjunction();

        if(keyword != null && !keyword.trim().isEmpty()){
            String trimmedKeyword = keyword.trim().toLowerCase();
            specification = specification.and((root, query, cb) ->
                cb.like(cb.lower(root.get("name")), "%" + trimmedKeyword + "%")
            );
        }

        if(categoryId != null){
            specification = specification.and((root, query, cb) -> {
                Join<Object, Object> categoryJoin = root.join("category", JoinType.INNER);
                return cb.equal(categoryJoin.get("id"), categoryId);
            });
        }

        return productRepository.findAll(specification, pageable);
    }

    public void save(Product product){
        productRepository.save(Objects.requireNonNull(product));
    }

    public Product getById(Integer id){
        return productRepository.findById(Objects.requireNonNull(id)).orElse(null);
    }

    public void update(Product product){
        productRepository.save(Objects.requireNonNull(product));
    }

    public void delete(Integer id){
        productRepository.deleteById(Objects.requireNonNull(id));
    }
}