package com.ecommerce.ThymeleafEcommerce.controller;

import com.ecommerce.ThymeleafEcommerce.dto.AdminDto;
import com.ecommerce.ThymeleafEcommerce.model.Admin;
import com.ecommerce.ThymeleafEcommerce.service.AdminService;
import com.ecommerce.ThymeleafEcommerce.service.impl.AdminServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class LoginController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AdminServiceImpl adminService;

    @RequestMapping("/index")
    public String home(Principal principal){
        String username;
        username = principal.getName();
        System.out.println(username);
        return "index";
    }
    @GetMapping("/login")
    public String LoginForm(){
        return "login";
    }
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }
    @GetMapping("/forgot-password")
    public String forgotPassword(Model model){
        return "forgot-password";
    }
    @PostMapping("/register")
    public String add(@Valid @ModelAttribute("adminDto") AdminDto adminDto, BindingResult result,
                      Model model, RedirectAttributes attributes, HttpSession session){
        try {
            System.out.println(adminDto.toString());
            if(result.hasErrors()){
                model.addAttribute("adminDto",adminDto);
                System.out.println("Email already registered." + result.getAllErrors());
                return "register";
            }
            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);
            if(admin != null){
                model.addAttribute("adminDto",adminDto);
                model.addAttribute("emailError", "Email already registered.");
                System.out.println("Email already registered.");
                return "register";
            }
            if(adminDto.getPassword().equals(adminDto.getRepeatPassword())){
                System.out.println("yes");
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                model.addAttribute("adminDto",adminDto);
                attributes.addFlashAttribute("success", "Successfully registered.");
                System.out.println("Successfully registered.");
            }else {
                model.addAttribute("adminDto",adminDto);
                model.addAttribute("passwordError", "Password aren't the same.");
                System.out.println("Password aren't the same.");
                return "register";
            }

        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errors", "Server error");
        }
        return "register";
    }

}
