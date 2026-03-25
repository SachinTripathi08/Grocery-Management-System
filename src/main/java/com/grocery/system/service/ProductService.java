package com.grocery.system.service;

import com.grocery.system.entity.Product;
import com.grocery.system.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() { return productRepository.findAll(); }
    public Product findById(Long id) { return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found")); }
    public Product save(Product product) { 
        if(product.getSellingPrice() > product.getMrp()) {
            throw new IllegalArgumentException("Selling price cannot be greater than MRP");
        }
        return productRepository.save(product); 
    }
    public void deleteById(Long id) { productRepository.deleteById(id); }
    public List<Product> search(String name) { return productRepository.findByNameContainingIgnoreCase(name); }
    
    public void reduceStock(Long productId, int quantity) {
        Product product = findById(productId);
        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock for product: " + product.getName());
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }
}
