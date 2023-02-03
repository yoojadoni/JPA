package com.example.demo.entity;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderDetailDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "price", "quantity", "status"})
@DynamicUpdate
public class Orders extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    public Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private Long price;
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @ColumnDefault("'PaySuccess'")
    private OrderStatusEnum status;

    @Column(name = "address_code")
    private String addressCode;

    @Column(name = "address_detail")
    private String addressDetail;

    // 배달비
    @Column(name = "delivery_cost")
    private Long deliveryCost;

    // 배달완료된 시간
    @JsonFormat(pattern = "yyyyMMddHHmm")
    @Column(name = "delivery_time")
    private LocalDateTime deliveryTime;

    // 배달 도착 예정시간
    @JsonFormat(pattern = "yyyyMMddHHmm")
    @Column(name = "delivery_expc_time")
    private LocalDateTime deliveryExpcTime;


    @PrePersist
    public void prePersist() {
        this.status = this.status == null ? OrderStatusEnum.valueOf("OrderComplete") : this.status;
    }
    /**
     * cascade TYPE
     * : ALL 전체
     *   PERSIST 영속(저장시)
     *   REMOVE 삭제
     */
    @OneToMany(mappedBy = "orders",  cascade = CascadeType.ALL)
    List<OrdersDetail> ordersDetails = new ArrayList<>();

    //양방향 연관관계 편의 method
    public void addOrdersDetails(OrdersDetail ordersDetail){
        ordersDetails.add(ordersDetail);
        ordersDetail.setOrders(this);
    }

    public Orders(Long price, int quantity){
        this.price = price;
        this.quantity = quantity;
    }

    public Orders(Long price, int quantity, OrderStatusEnum status){
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    /**
     * orderDto -> orders Entity mapping 처리.
     * @param orderDto
     * @return Orders
     */
   public static Orders createOrdersFromDto(OrderDTO orderDto){
        Orders orders = new Orders();
        orders.id = orderDto.getId();
        orders.price = orderDto.getPrice();
        orders.quantity = orderDto.getQuantity();
        orders.status = orderDto.getStatus();
        orders.member = orderDto.getMember();
        for (OrderDetailDTO ordersDetailDto : orderDto.getOrdersDetails()) {
            OrdersDetail ordersDetail = OrdersDetail.createOrdersDetailFromDto(ordersDetailDto);
            orders.addOrdersDetails(ordersDetail);
        }
        return orders;
    }

    /*public static Orders createOrdersFromRequestDTO(com.example.demo.dto.order.OrderDTO.Request orderDto){
        Orders orders = new Orders();
        orders.id = orderDto.getId();
        orders.price = orderDto.getPrice();
        orders.quantity = orderDto.getQuantity();
        orders.status = orderDto.getStatus();
        orders.member = orderDto.getMember();
        for (com.example.demo.dto.order.OrderDetailDTO ordersDetailDto : orderDto.getOrdersDetails()) {
            OrdersDetail ordersDetail = OrdersDetail.createOrdersDetailFromRequestDTO(ordersDetailDto);
            orders.addOrdersDetails(ordersDetail);
        }
        return orders;
    }*/

    @Builder
    public Orders(Long id, Long price, int quantity, OrderStatusEnum status, List<OrdersDetail> ordersDetails, Member member, Shop shop,String addressCode, String addressDetail, Long deliveryCost, LocalDateTime deliveryTime, LocalDateTime deliveryExpcTime) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.ordersDetails = ordersDetails;
        this.member = member;
        this.shop = shop;
        this.addressCode = addressCode;
        this.addressDetail = addressDetail;
        this.deliveryCost = deliveryCost;
        this.deliveryTime = deliveryTime;
        this.deliveryExpcTime = deliveryExpcTime;
    }


}
