package com.grocery.system.service;

import com.grocery.system.entity.Customer;
import com.grocery.system.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> findAll() { return customerRepository.findAll(); }
    public Customer findById(Long id) { return customerRepository.findById(id).orElse(null); }
    public Customer save(Customer customer) { return customerRepository.save(customer); }
    public void deleteById(Long id) { customerRepository.deleteById(id); }
}
