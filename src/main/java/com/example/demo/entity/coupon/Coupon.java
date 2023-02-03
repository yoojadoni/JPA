package com.example.demo.entity.coupon;

import com.example.demo.entity.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString(of ={"id", "name", "discountType", "discountPrice", "couponStatus", "endDate"})
@Table(name = "coupon")
@DynamicInsert
public class Coupon extends BaseTimeEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id", columnDefinition = "VARCHAR(30)")
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    private DiscountTypeEnum discountType;

    @Column(name = "discount_price")
    private int discountPrice;

    @Enumerated(EnumType.STRING)
//    @Builder.Default
    @Column(name = "coupon_status", columnDefinition = "VARCHAR(128)")
    private CouponStatusEnum couponStatus;

    @Column(name = "end_date")
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime endDate;

    @PrePersist
    public void prePersist() {
        this.couponStatus = this.couponStatus == null ? CouponStatusEnum.NORMAL : this.couponStatus;
    }

    @Builder
    public Coupon(String id, String name, DiscountTypeEnum discountType, int discountPrice, CouponStatusEnum couponStatus, LocalDateTime endDate) {
        this.id = id;
        this.name = name;
        this.discountType = discountType;
        this.discountPrice = discountPrice;
        this.couponStatus = couponStatus;
        this.endDate = endDate;
    }
}
