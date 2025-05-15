package com.example.motorbikebooking.repository;

import com.example.motorbikebooking.entity.RentalOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalOrderRepository extends JpaRepository<RentalOrder, Long> {
    // Tổng số đơn hàng
    long count();

    // Tổng số đơn hàng "Đặt thành công"
    long countByStatus(String status);
}