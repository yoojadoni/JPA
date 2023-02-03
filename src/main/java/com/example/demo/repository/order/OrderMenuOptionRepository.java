package com.example.demo.repository.order;

import com.example.demo.entity.order.OrderMenuOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuOptionRepository extends JpaRepository<OrderMenuOption, Long> {
}
