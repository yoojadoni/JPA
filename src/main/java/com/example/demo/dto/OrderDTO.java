package com.example.demo.dto;

import com.example.demo.entity.Member;
import com.example.demo.entity.coupon.Coupon;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString(of = {"id", "price", "quantity", "status"})
public class OrderDTO {

    public Long id;
    public Long shopId;
    public Long price;
    public int quantity;
    public com.example.demo.entity.OrderStatusEnum status;
    @Builder.Default
    List<OrderDetailDTO> ordersDetails = new ArrayList<OrderDetailDTO>();
    @Builder.Default
    public List<Coupon> couponList = new ArrayList<Coupon>();

    public Member member;
    @Builder
    public static OrderDTO toDto(com.example.demo.entity.Orders orders){
        return OrderDTO.builder()
                .id(orders.getId())
                .quantity(orders.getQuantity())
                .price(orders.getPrice())
                .status(orders.getStatus())
                .ordersDetails(OrderDetailDTO.ordersDetailList(orders.getOrdersDetails()))
                .build();
    }
}
