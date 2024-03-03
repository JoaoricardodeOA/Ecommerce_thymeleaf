package com.ecommerce.ThymeleafEcommerce.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "TB_ADMIN")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADMIN_ID")
    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    @Lob
    @Column(columnDefinition = "CLOB")
    private String image;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "ADMIN_ROLES",joinColumns = @JoinColumn(name = "ADMIN_ID",referencedColumnName = "ADMIN_ID"),
    inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID"))
    private Collection<Role> roles;

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", roles=" + roles +
                '}';
    }
}
