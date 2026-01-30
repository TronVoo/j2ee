package com.example.bai2.service;


import com.example.bai2.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private List<Book> books = new ArrayList<>();

    // Lấy tất cả sách
    public List<Book> getAllBooks() {
        return books;
    }

    // Lấy sách theo ID
    public Book getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Thêm sách
    public void addBook(Book book) {
        books.add(book);
    }

    // Cập nhật sách
    public void updateBook(int id, Book updatedBook) {
        books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .ifPresent(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                });
    }

    // Xoá sách
    public void deleteBook(int id) {
        books.removeIf(book -> book.getId() == id);
    }
}

