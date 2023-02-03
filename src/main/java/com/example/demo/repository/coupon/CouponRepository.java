package com.example.demo.repository.coupon;

import com.example.demo.entity.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findById(Long id);
}
