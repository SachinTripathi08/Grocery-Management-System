package com.grocery.system.repository;

import com.grocery.system.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByCustomer_IdOrderByBillDateDesc(Long customerId);
}
