package com.example.bai3.service;

import com.example.bai3.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private List<Book> books = new ArrayList<>();
    private int nextId = 1;

    // Lấy danh sách sách
    public List<Book> getAllBooks() {
        return books;
    }

    // Thêm sách
    public void addBook(Book book) {
        book.setId(nextId++);
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

    // Lấy sách theo ID
    public Book getBookById(int id) {
        return books.stream() .filter(book -> book.getId() == id).findFirst().orElse(null);
        }

    // Xóa sách
    public void deleteBook(int id) {
        books.removeIf(book -> book.getId() == id);
    }
}