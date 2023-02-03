package com.example.demo.repository.coupon;

import com.example.demo.entity.coupon.Coupon;
import com.example.demo.entity.coupon.CouponStatusEnum;
import com.example.demo.entity.coupon.IssueCoupon;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.entity.coupon.QIssueCoupon.issueCoupon;

@RequiredArgsConstructor
@Repository
public class IssueCouponQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<IssueCoupon> findByIssueCouponByCouponId(List<Coupon> couponList, CouponStatusEnum couponStatus){
        return queryFactory
                .selectFrom(issueCoupon)
                .join(issueCoupon.coupon)
                .where(couponEqIn(couponList)
                        ,couponStatusEq(couponStatus)
                        )
                .fetch();
    }

    private BooleanExpression couponEqIn(List<Coupon> couponList) {
        return (couponList.isEmpty() || couponList.size() == 0) ? null : issueCoupon.coupon.in(couponList);
    }

    private BooleanExpression couponStatusEq(CouponStatusEnum couponStatus){
        return couponStatus != null ?  issueCoupon.couponStatus.eq(couponStatus) : null;
    }
}
