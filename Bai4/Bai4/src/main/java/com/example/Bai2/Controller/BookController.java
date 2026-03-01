package com.example.Bai2.Controller;

import com.example.Bai2.Model.Book;
import com.example.Bai2.Model.Category;
import com.example.Bai2.service.BookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private static final String UPLOAD_DIR = "uploads/";

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", Category.values());
        return "add-book";
    }

    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute Book book, BindingResult result, 
                         @RequestParam("imageFile") MultipartFile file, Model model, RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            model.addAttribute("categories", Category.values());
            return "add-book";
        }
        
        // Xử lý upload file
        if (!file.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);
                Files.createDirectories(uploadPath);
                Files.write(uploadPath.resolve(fileName), file.getBytes());
                book.setImage(fileName);
            } catch (IOException e) {
                model.addAttribute("error", "Lỗi khi tải file: " + e.getMessage());
                model.addAttribute("categories", Category.values());
                return "add-book";
            }
        } else {
            // Nếu không có file, để trống image để template hiển thị placeholder
            book.setImage(null);
        }
        
        bookService.addBook(book);
        redirectAttrs.addFlashAttribute("success", "Thêm sách thành công ✅");
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable Integer id, Model model) {
        bookService.getBookById(id).ifPresent(book -> model.addAttribute("book", book));
        model.addAttribute("categories", Category.values());
        return "edit-book";
    }

    @PostMapping("/edit")
    public String updateBook(@Valid @ModelAttribute Book book, BindingResult result,
                            @RequestParam(value = "imageFile", required = false) MultipartFile file, Model model, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            model.addAttribute("categories", Category.values());
            return "edit-book";
        }
        
        // Xử lý upload file nếu có
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);
                Files.createDirectories(uploadPath);
                Files.write(uploadPath.resolve(fileName), file.getBytes());
                book.setImage(fileName);
            } catch (IOException e) {
                model.addAttribute("error", "Lỗi khi tải file: " + e.getMessage());
                model.addAttribute("categories", Category.values());
                return "edit-book";
            }
        } else {
            // Nếu form không gửi image (hoặc gửi rỗng), lấy lại ảnh cũ từ DB để không bị mất ảnh
            if (book.getId() != null && (book.getImage() == null || book.getImage().isBlank())) {
                bookService.getBookById(book.getId()).ifPresent(existing -> book.setImage(existing.getImage()));
            }
        }
        
        bookService.updateBook(book);
        redirectAttrs.addFlashAttribute("success", "Cập nhật sách thành công ✅");
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}