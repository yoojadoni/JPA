package com.example.demo.entity.coupon;

import com.example.demo.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of ={"id", "member", "coupon", "couponStatus", "createdDate"})
@Table(name = "issue_coupon")
@DynamicInsert
public class IssueCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_coupon_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_status", columnDefinition = "VARCHAR(128)")
    private CouponStatusEnum couponStatus;

    @Column(name = "created_date", insertable = true, updatable = false)
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist() {
        this.couponStatus = this.couponStatus == null ? CouponStatusEnum.NORMAL : this.couponStatus;
        this.createdDate = LocalDateTime.now();
    }

    @Builder
    public IssueCoupon(Long id, Member member, Coupon coupon, CouponStatusEnum couponStatus, LocalDateTime createdDate) {
        this.id = id;
        this.member = member;
        this.coupon = coupon;
        this.couponStatus = couponStatus;
        this.createdDate = createdDate;
    }

    public void changeStatus(CouponStatusEnum couponStatus){
        this.couponStatus = couponStatus;
    }
}
