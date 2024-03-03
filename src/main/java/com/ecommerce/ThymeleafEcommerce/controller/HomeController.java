package com.ecommerce.ThymeleafEcommerce.controller;

import com.ecommerce.ThymeleafEcommerce.model.Category;
import com.ecommerce.ThymeleafEcommerce.model.Product;
import com.ecommerce.ThymeleafEcommerce.service.CategoryService;
import com.ecommerce.ThymeleafEcommerce.service.ProductService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JavaMailSender emailSender;

    @GetMapping("/home")
    public String home(Model model){
        List<Category> categories = categoryService.findByActivatedTrue();
        List<Product> products = productService.findAll();
        model.addAttribute("products",products);
        model.addAttribute("categories",categories);
        return "home";
    }
}
