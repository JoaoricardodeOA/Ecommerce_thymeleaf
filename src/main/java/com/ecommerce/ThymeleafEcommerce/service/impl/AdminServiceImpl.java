package com.ecommerce.ThymeleafEcommerce.service.impl;

import com.ecommerce.ThymeleafEcommerce.dto.AdminDto;
import com.ecommerce.ThymeleafEcommerce.model.Admin;
import com.ecommerce.ThymeleafEcommerce.repository.AdminRepository;
import com.ecommerce.ThymeleafEcommerce.repository.RoleRepository;
import com.ecommerce.ThymeleafEcommerce.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public Admin save(AdminDto adminDto) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminDto,admin);
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        System.out.println(admin.toString());
        return adminRepository.save(admin);
    }
}
