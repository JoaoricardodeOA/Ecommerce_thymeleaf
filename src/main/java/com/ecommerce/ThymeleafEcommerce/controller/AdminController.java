package com.ecommerce.ThymeleafEcommerce.controller;


import com.ecommerce.ThymeleafEcommerce.model.Category;
import com.ecommerce.ThymeleafEcommerce.model.Role;
import com.ecommerce.ThymeleafEcommerce.repository.AdminRepository;
import com.ecommerce.ThymeleafEcommerce.service.AdminService;
import com.ecommerce.ThymeleafEcommerce.service.impl.CategoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AdminController {
    @Autowired
    public CategoryServiceImpl service;
    @Autowired
    public AdminService adminService;

    @GetMapping("/categories")
    public String Categories(Model model, Principal principal){
        List<Role> teste = adminService.findByUsername(principal.getName()).getRoles().stream().filter(role -> role.getName().equals("ADMIN")).collect(Collectors.toList());
        if(teste.isEmpty()){
            System.out.println("teste");
        }
        //System.out.println(adminService.findByUsername(principal.getName()).getRoles().contains(new SimpleGrantedAuthority("ADMIN"));
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
        //    return "redirect:/login";
        //}
        List<Category> categories = service.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("size",categories.size());
        model.addAttribute("title","Category");
        model.addAttribute("categoryNew", new Category());
        return "categories";
    }
    @PostMapping("/save-category")
    public String add(@ModelAttribute("categoryNew") Category category, Model model, RedirectAttributes attributes){
        try {
            service.save(category);
            model.addAttribute("categoryNew", category);
            attributes.addFlashAttribute("success", "Add successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            attributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            model.addAttribute("categoryNew", category);
            attributes.addFlashAttribute("error",
                    "Error server");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/findById", method = {RequestMethod.PUT,RequestMethod.GET})
    @ResponseBody
    public Optional<Category> findById(Long id){
        return service.getById(id);
    }

    @GetMapping("/update-category")
    public String update(Category category, RedirectAttributes attributes){
        try {
            service.update(category);
            attributes.addFlashAttribute("success", "Update successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            attributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            attributes.addFlashAttribute("error", "Error from server or duplicate name of category, please check again!");
        }
        return "redirect:/categories";
    }
    @RequestMapping(value = "/delete-category", method = {RequestMethod.PUT,RequestMethod.GET})
    public String delete(Long id, RedirectAttributes attributes){
        try {
            service.deleteById(id);
            attributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (Exception e2) {
            e2.printStackTrace();
            attributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/categories";
    }
    @RequestMapping(value = "/enable-category", method = {RequestMethod.PUT,RequestMethod.GET})
    public String enable(Long id , RedirectAttributes attributes){
        try {
            service.enableById(id);
            attributes.addFlashAttribute("success","Enabled successfully!");
        }catch (Exception e2){
            e2.printStackTrace();
            attributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/categories";
    }
}
