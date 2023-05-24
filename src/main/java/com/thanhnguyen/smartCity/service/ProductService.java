package com.thanhnguyen.smartCity.service;

import com.thanhnguyen.smartCity.model.Product;
import com.thanhnguyen.smartCity.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepo;

    public void saveProductToDB(MultipartFile file, String name, int fees) {
        Product product = new Product();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("not a a valid file");
        }
        try {
            product.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        product.setName(name);
        product.setFees(fees);

        productRepo.save(product);
    }

    public List<Product> getAllProduct() {
        return productRepo.findAll();
    }

    public void deleteProductById(int productId) {
        productRepo.deleteById(productId);
    }

    public void chageProductName(int productId, String name) {
        Product product = new Product();
        product = productRepo.findById(productId).get();
        product.setName(name);
        productRepo.save(product);
    }

    public void changeProductPrice(int productId, int fees) {
        Product p = new Product();
        p = productRepo.findById(productId).get();
        p.setFees(fees);
        productRepo.save(p);
    }
}