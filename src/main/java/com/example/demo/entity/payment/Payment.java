package com.example.demo.entity.payment;

import com.example.demo.entity.Orders;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Embeddable
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payment {

    @EmbeddedId
    private PaymentPK paymentPK;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_status")
    private PayStatusEnum payStatus;

    //결제 금액
    @Column(name = "pay_price")
    private Long payPrice;

    @Builder
    public Payment(PaymentPK paymentPK, Orders orders, PayStatusEnum payStatus, Long payPrice) {
        this.paymentPK = paymentPK;
        this.orders = orders;
        this.payStatus = payStatus;
        this.payPrice = payPrice;
    }

    public void changeStatus(PayStatusEnum payStatus){
        this.payStatus = payStatus;
    }
}
