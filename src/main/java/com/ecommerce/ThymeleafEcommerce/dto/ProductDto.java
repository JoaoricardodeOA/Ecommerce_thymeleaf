package com.ecommerce.ThymeleafEcommerce.dto;

import com.ecommerce.ThymeleafEcommerce.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private int currentQuantity;
    private double costPrice;
    private double salePrice;
    private String image;
    private Category category;
    private boolean is_activated;
    private boolean is_deleted;
}
