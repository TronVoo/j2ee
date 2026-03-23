package com.example.LAB05.controller;

import com.example.LAB05.model.Product;
import com.example.LAB05.service.CartService;
import com.example.LAB05.service.ProductService;
import com.example.LAB05.service.CategoryService;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/products")

public class ProductController {

    private static final String IMAGE_UPLOAD_DIR = "src/main/resources/static/images";

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CartService cartService;


    @GetMapping
    public String index(Model model,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword,
                        @RequestParam(name = "categoryId", required = false) String categoryId,
                        @RequestParam(name = "sort", defaultValue = "") String sort,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        HttpSession session){

        Integer selectedCategoryId = null;
        if (categoryId != null && !categoryId.isBlank()) {
            try {
                selectedCategoryId = Integer.parseInt(categoryId.trim());
            } catch (NumberFormatException ignored) {
                selectedCategoryId = null;
            }
        }

        Page<Product> productPage = productService.searchProducts(keyword, selectedCategoryId, page, 5, sort);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("hasNext", productPage.hasNext());
        model.addAttribute("hasPrevious", productPage.hasPrevious());

        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", selectedCategoryId);
        model.addAttribute("sort", sort);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("cartCount", cartService.getTotalQuantity(session));

        return "product/products";
    }

    private void setCartCount(Model model, HttpSession session){
        model.addAttribute("cartCount", cartService.getTotalQuantity(session));
    }



    @GetMapping("/create")
    public String create(Model model, HttpSession session){

        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        setCartCount(model, session);

        return "product/create";
    }


    @PostMapping("/create")
    public String create(@Valid Product product,
                         BindingResult result,
                         @RequestParam("imageProduct") MultipartFile file,
                         Model model,
                         HttpSession session){

        if(result.hasErrors()){
            model.addAttribute("categories", categoryService.getAll());
            setCartCount(model, session);
            return "product/create";
        }

        if(!file.isEmpty()){
            product.setImage(storeImage(file));
        }

        // gán category theo id
        if(product.getCategory() != null){
            product.setCategory(
                categoryService.getById(product.getCategory().getId())
            );
        }

        productService.save(product);

        return "redirect:/products";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model, HttpSession session){

        Product product = productService.getById(id);

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAll());
        setCartCount(model, session);

        return "product/edit";
    }


    @PostMapping("/edit")
    public String edit(@Valid Product product,
                       BindingResult result,
                       @RequestParam("imageProduct") MultipartFile file,
                       Model model,
                       HttpSession session){

        if(result.hasErrors()){
            model.addAttribute("categories", categoryService.getAll());
            setCartCount(model, session);
            return "product/edit";
        }

        if(!file.isEmpty()){
            product.setImage(storeImage(file));
        }

        if(product.getCategory() != null){
            product.setCategory(
                categoryService.getById(product.getCategory().getId())
            );
        }

        productService.update(product);

        return "redirect:/products";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){

        productService.delete(id);

        return "redirect:/products";
    }

    private String storeImage(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(IMAGE_UPLOAD_DIR);
            Files.createDirectories(uploadPath);

            String uploadedName = file.getOriginalFilename();
            String originalName = uploadedName == null ? "image" : uploadedName;
            String safeName = originalName.replaceAll("[^a-zA-Z0-9._-]", "_");
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
            String fileName = timestamp + "_" + safeName;

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Không thể lưu ảnh sản phẩm", e);
        }
    }

}
