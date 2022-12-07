package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

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
    public int price;
    public int quantity;
    public com.example.demo.entity.OrderStatusEnum status;
    @Builder.Default
    List<OrderDetailDTO> ordersDetails = new ArrayList<OrderDetailDTO>();

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
