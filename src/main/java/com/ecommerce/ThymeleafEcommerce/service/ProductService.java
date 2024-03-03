package com.ecommerce.ThymeleafEcommerce.service;

import com.ecommerce.ThymeleafEcommerce.dto.ProductDto;
import com.ecommerce.ThymeleafEcommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(Long id);

    Product save(MultipartFile imageProduct, ProductDto productDto);

    Product update(MultipartFile imageProduct,ProductDto productDto);
    void deleteById(Long id);
    void enableById(Long id);

    Page<Product> pageProducts(int pageNo);

    Page<Product> searchProducts(int pageNo,String keyword);
}
