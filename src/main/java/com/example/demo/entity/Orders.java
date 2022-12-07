package com.example.demo.entity;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderDetailDTO;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "price", "quantity", "status"})
public class Orders extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private int price;
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @ColumnDefault("'PaySuccess'")
    private OrderStatusEnum status;

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

    public Orders(int price, int quantity){
        this.price = price;
        this.quantity = quantity;
    }

    public Orders(int price, int quantity, OrderStatusEnum status){
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
        for (OrderDetailDTO ordersDetailDto : orderDto.getOrdersDetails()) {
            OrdersDetail ordersDetail = OrdersDetail.createOrdersDetailFromDto(ordersDetailDto);
            orders.addOrdersDetails(ordersDetail);
        }
        return orders;
    }
}
