package com.ecommerce.ThymeleafEcommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "city_id")
    private Long id;

    private String name;
}
