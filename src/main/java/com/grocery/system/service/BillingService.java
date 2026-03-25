package com.grocery.system.service;

import com.grocery.system.entity.Bill;
import com.grocery.system.entity.BillItem;
import com.grocery.system.entity.Product;
import com.grocery.system.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillRepository billRepository;
    private final ProductService productService;

    @Transactional
    public Bill createBill(Bill bill) {
        double subtotal = 0;
        double totalDiscount = 0;
        double finalAmount = 0;

        for (BillItem item : bill.getItems()) {
            Product product = productService.findById(item.getProduct().getId());
            
            // Set snapshot prices
            item.setMrp(product.getMrp());
            item.setSellingPrice(product.getSellingPrice());
            item.setDiscount(product.getDiscount());
            item.setFinalPrice(product.getFinalPrice());
            item.setBill(bill);

            // Deduct stock
            productService.reduceStock(product.getId(), item.getQuantity());

            // Calculate totals
            subtotal += item.getSellingPrice() * item.getQuantity();
            finalAmount += item.getFinalPrice() * item.getQuantity();
        }

        totalDiscount = subtotal - finalAmount;

        bill.setSubtotal(subtotal);
        bill.setTotalDiscount(totalDiscount);
        bill.setFinalAmount(finalAmount);

        return billRepository.save(bill);
    }

    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    public Bill findById(Long id) {
        return billRepository.findById(id).orElseThrow(() -> new RuntimeException("Bill not found"));
    }
}
