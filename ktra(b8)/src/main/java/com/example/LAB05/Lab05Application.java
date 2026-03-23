package com.example.LAB05;

import com.example.LAB05.model.Category;
import com.example.LAB05.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Objects;

@SpringBootApplication
public class Lab05Application {

	public static void main(String[] args) {
		SpringApplication.run(Lab05Application.class, args);
	}

	@Bean
	CommandLineRunner initData(CategoryRepository categoryRepository) {
		return args -> {
			if (categoryRepository.count() == 0) {
				Category c1 = new Category();
				c1.setName("Điện thoại");
				Category c2 = new Category();
				c2.setName("Đồ gia dụng");
				Category c3 = new Category();
				c3.setName("Máy tính");
				categoryRepository.saveAll(Objects.requireNonNull(Arrays.asList(c1, c2, c3)));
				System.out.println("Đã khởi tạo dữ liệu danh mục mẫu.");
			}
		};
	}

}
