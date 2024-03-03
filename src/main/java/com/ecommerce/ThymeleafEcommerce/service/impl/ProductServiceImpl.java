package com.ecommerce.ThymeleafEcommerce.service.impl;

import com.ecommerce.ThymeleafEcommerce.dto.ProductDto;
import com.ecommerce.ThymeleafEcommerce.model.Product;
import com.ecommerce.ThymeleafEcommerce.repository.ProductRepository;
import com.ecommerce.ThymeleafEcommerce.service.ProductService;
import com.ecommerce.ThymeleafEcommerce.utils.ImageUpload;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository repository;

    @Autowired
    private ImageUpload imageUpload;
    @Override
    public List<Product> findAll() {
        return  repository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return repository.findById(id).get();
    }


    @Override
    public Product save(MultipartFile imageProduct, ProductDto productDto) {
        try{
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        product.set_deleted(false);
        product.set_activated(true);
        if(imageProduct == null){
            product.setImage(null);
        }else{
            if(imageUpload.uploadImage(imageProduct)){
                System.out.println("Added successfully");
            }
            product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
        }
        return repository.save(product);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product productUpdate = repository.getReferenceById(productDto.getId());
            if (imageProduct.getBytes().length > 0) {
                if (imageUpload.checkExist(imageProduct)) {
                    productUpdate.setImage(productUpdate.getImage());
                } else {
                    imageUpload.uploadImage(imageProduct);
                    productUpdate.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
                }
            }
            productUpdate.setCategory(productDto.getCategory());
            productUpdate.setId(productUpdate.getId());
            productUpdate.setName(productDto.getName());
            productUpdate.setDescription(productDto.getDescription());
            productUpdate.setCostPrice(productDto.getCostPrice());
            productUpdate.setSalePrice(productDto.getSalePrice());
            productUpdate.setCurrentQuantity(productDto.getCurrentQuantity());
            return repository.save(productUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        Product product = repository.findById(id).get();
        product.set_deleted(true);
        product.set_activated(false);
        repository.save(product);
    }

    @Override
    public void enableById(Long id) {
        Product product = repository.findById(id).get();
        product.set_deleted(false);
        product.set_activated(true);
        repository.save(product);
    }

    @Override
    public Page<Product> pageProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo,5);
        Page<Product> productPage = repository.pageProduct(pageable);
        return productPage;
    }

    @Override
    public Page<Product> searchProducts(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo,5);
        Page<Product> products = repository.searchProducts(pageable,keyword);
        return products;
    }

}
