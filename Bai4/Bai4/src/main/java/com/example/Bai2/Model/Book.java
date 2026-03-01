package com.example.Bai2.Model;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Tên sách không được để trống")
    @Size(min = 3, max = 100, message = "Tên sách phải từ 3 đến 100 ký tự")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "Tên tác giả không được để trống")
    @Size(min = 3, max = 100, message = "Tên tác giả phải từ 3 đến 100 ký tự")
    @Column(nullable = false)
    private String author;
    
    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    @DecimalMax(value = "999999.0", message = "Giá không được vượt quá 999999")
    @Column(nullable = false)
    private Double price;
    
    @Column
    private String image;
    
    @NotNull(message = "Thể loại không được để trống")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;
}
