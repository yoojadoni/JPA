package com.example.demo.repository.order;

import com.example.demo.entity.Orders;
import com.example.demo.entity.coupon.Coupon;
import com.example.demo.entity.coupon.CouponStatusEnum;
import com.example.demo.entity.coupon.IssueCoupon;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.entity.coupon.QIssueCoupon.issueCoupon;
import static com.example.demo.entity.QOrders.orders;

@RequiredArgsConstructor
@Repository
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

}
