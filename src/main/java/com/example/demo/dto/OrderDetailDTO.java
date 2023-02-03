package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetailDTO {

    public Long id;

    public Long price;
    public int amount;
    public Long menuId;
    public String menuName;

    public OrderDetailDTO(com.example.demo.entity.OrdersDetail ordersDetail){
        this.id = ordersDetail.getId();
        this.price = ordersDetail.getPrice();
        this.amount = ordersDetail.getAmount();
        this.menuId = ordersDetail.getMenu().getId();
        this.menuName = ordersDetail.getMenu().getMenuName();
    }
    static List<OrderDetailDTO> ordersDetailList(List<com.example.demo.entity.OrdersDetail> ordersDetailEntity){
        List<OrderDetailDTO> ordersDetails = new ArrayList<>();
        ordersDetailEntity.forEach(v-> {
            ordersDetails.add(new OrderDetailDTO(v));
        });
        return ordersDetails;
    }
}
