package com.example.demo.repository.coupon;

import com.example.demo.entity.coupon.Coupon;
import com.example.demo.entity.coupon.CouponStatusEnum;
import com.example.demo.entity.coupon.IssueCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueCouponRepository extends JpaRepository<IssueCoupon, Long> {


    Optional<IssueCoupon> findByIdAndCouponStatus(Long id, CouponStatusEnum couponStatus);

    /*
    Inner Join이 필요하므로 주석.
    아래의 쿼리는 left join
    @EntityGraph(attributePaths = {"coupon"})
    Optional<IssueCoupon> findByCouponAndCouponStatus(Coupon coupon, CouponStatusEnum couponStatus);
    */

    @Query("select i.coupon from IssueCoupon i where i.coupon in (:coupon) and i.couponStatus = :couponStatus")
    List<Coupon> findByCouponInAndCouponStatusCustom(
            @Param(value = "coupon") List<Coupon> coupon
            ,@Param(value = "couponStatus") CouponStatusEnum couponStatus
            );

//    Optional<List<IssueCoupon>> findByCouponInAndCouponStatus(List<Coupon> coupon, CouponStatusEnum couponStatusEnum);
    List<IssueCoupon> findByCouponInAndCouponStatus(List<Coupon> coupon, CouponStatusEnum couponStatusEnum);


}
