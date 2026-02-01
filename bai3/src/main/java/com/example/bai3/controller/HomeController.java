package com.example.bai3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/")
    public String root() {
        return "redirect:/books";
    }

    @GetMapping("/home")
    public String home() {
        return "redirect:/books";
    }
    
}