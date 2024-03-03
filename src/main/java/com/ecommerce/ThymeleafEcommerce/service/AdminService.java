package com.ecommerce.ThymeleafEcommerce.service;

import com.ecommerce.ThymeleafEcommerce.dto.AdminDto;
import com.ecommerce.ThymeleafEcommerce.model.Admin;

public interface AdminService {
    Admin findByUsername(String username);

    Admin save(AdminDto adminDto);
}
