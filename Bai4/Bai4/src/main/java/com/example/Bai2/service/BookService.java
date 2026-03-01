package com.example.Bai2.service;

import com.example.Bai2.Model.Book;
import com.example.Bai2.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void addBook(Book book) {
        // Đảm bảo luôn tạo bản ghi mới: loại bỏ id nếu có để tránh ghi đè bản ghi cũ
        book.setId(null);
        bookRepository.save(book);
    }

    public Optional<Book> getBookById(int id) {
        return bookRepository.findById(id);
    }

    public void updateBook(Book updatedBook) {
        if (bookRepository.existsById(updatedBook.getId())) {
            bookRepository.save(updatedBook);
        }
    }

    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }
}