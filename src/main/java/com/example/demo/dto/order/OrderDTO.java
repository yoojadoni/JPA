package com.example.demo.dto.order;

import com.example.demo.dto.MenuOptionDTO;
import com.example.demo.dto.order.OrderDetailDTO;
import com.example.demo.entity.Member;
import com.example.demo.entity.Orders;
import com.example.demo.entity.OrdersDetail;
import com.example.demo.entity.Shop;
import com.example.demo.entity.coupon.Coupon;
import com.example.demo.entity.menu.Menu;
import com.example.demo.entity.order.OrderMenuOption;
import com.example.demo.entity.payment.Payment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ToString(of = {"id", "price", "quantity", "status"})
    public static class Request {
        public Long id;
        public Member member;
        public Long shopId;
        public Long price;
        public int quantity;
        public com.example.demo.entity.OrderStatusEnum status;
        List<OrderDetailDTO> ordersDetails = new ArrayList<OrderDetailDTO>();
        public List<Coupon> couponList = new ArrayList<Coupon>();

        public List<OrderMenuOptionDTO.Request> orderMenuOptionList = new ArrayList<>();

        String addressCode;
        String addressDetail;

        @Builder.Default
        Long deliveryCost = 0L;

        String deliveryExpcTime;

        Payment payment;

        public Orders toEntity(){

            Orders orders = Orders.builder()
                    .id(id)
                    .member(member)
                    .shop(Shop.builder().id(shopId).build())
                    .price(price)
                    .quantity(quantity)
                    .status(status)
                    .ordersDetails(new ArrayList<>())
                    .addressCode(addressCode)
                    .addressDetail(addressDetail)
                    .deliveryCost(deliveryCost)
                    .deliveryExpcTime(LocalDateTime.parse(deliveryExpcTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm")))
                    .build();

            List<OrdersDetail> ordersDetailList = this.ordersDetails.stream()
                    .map(e -> new OrdersDetail(
                                    e.getId()
                                    , orders
                                    , e.getPrice()
                                    , e.getAmount()
                                    , Menu.builder()
                                    .id(e.getMenuId())
                                    .menuName(e.getMenuName())
                                    .build()
                            )
                    ).collect(Collectors.toList());
            
            // 양방향 맵핑
            for (OrdersDetail ordersDetail : ordersDetailList) {
                orders.addOrdersDetails(ordersDetail);
            }
            return orders;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Request request = (Request) o;
            return quantity == request.quantity && Objects.equals(id, request.id) && Objects.equals(member, request.member) && Objects.equals(shopId, request.shopId) && Objects.equals(price, request.price) && status == request.status && Objects.equals(ordersDetails, request.ordersDetails) && Objects.equals(couponList, request.couponList) && Objects.equals(orderMenuOptionList, request.orderMenuOptionList) && Objects.equals(addressCode, request.addressCode) && Objects.equals(addressDetail, request.addressDetail) && Objects.equals(deliveryCost, request.deliveryCost) && Objects.equals(deliveryExpcTime, request.deliveryExpcTime) && Objects.equals(payment, request.payment);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, member, shopId, price, quantity, status, ordersDetails, couponList, orderMenuOptionList, addressCode, addressDetail, deliveryCost, deliveryExpcTime, payment);
        }
    }

    @Getter
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response{
        public Long id;
        public Long shopId;
        public Long price;
        public int quantity;
        public com.example.demo.entity.OrderStatusEnum status;
        List<OrderDetailDTO> ordersDetails = new ArrayList<OrderDetailDTO>();

        // Entity -> DTO 변환
        public Response(Orders orders){
            this.id = orders.getId();
            this.price = orders.getPrice();
            this.quantity = orders.getQuantity();
            this.status = orders.getStatus();
            this.shopId = orders.getShop().getId();
            this.ordersDetails = orders.getOrdersDetails().stream().map(OrderDetailDTO::new).collect(Collectors.toList());
        }
    }

}
