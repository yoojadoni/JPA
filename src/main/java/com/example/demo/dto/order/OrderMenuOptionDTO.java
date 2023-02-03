package com.example.demo.dto.order;

import com.example.demo.entity.OrdersDetail;
import com.example.demo.entity.menu.MenuOption;
import com.example.demo.entity.order.OrderMenuOption;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

public class OrderMenuOptionDTO {



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public Long id;

        public Long menuOptionId;

        public Long orderDetailId;

        public OrderMenuOption toEntity() {
            OrderMenuOption orderMenuOption = OrderMenuOption.builder()
                    .id(id)
                    .menuOption(MenuOption.builder()
                            .id(menuOptionId)
                            .build())
                    .ordersDetail(OrdersDetail.builder()
                            .id(orderDetailId)
                            .build())
                    .build();
            return orderMenuOption;
        }
    }

}
