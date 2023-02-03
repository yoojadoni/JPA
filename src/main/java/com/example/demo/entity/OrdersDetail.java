package com.example.demo.entity;

import com.example.demo.dto.OrderDetailDTO;
import com.example.demo.entity.menu.Menu;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of= {"id", "price", "amount"})
@Table(name = "ordersdetail")
public class OrdersDetail{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    Menu menu;

    //가격
    private Long price;
    //수량
    private int amount;

    public OrdersDetail(Long price, int amount, Menu menu){
        this.price = price;
        this.amount = amount;
        this.menu = menu;
    }



    public void changeOrders(Orders orders){
        this.orders = orders;
        orders.getOrdersDetails().add(this);
    }

    @Builder
    public OrdersDetail(Long id, Orders orders, Long price, int amount, Menu menu) {
        this.id = id;
        this.orders = orders;
        this.price = price;
        this.amount = amount;
        this.menu = menu;
    }

    public static OrdersDetail createOrdersDetailFromDto(OrderDetailDTO ordersDetailDto){
        OrdersDetail ordersDetail = new OrdersDetail();
        ordersDetail.id = ordersDetailDto.getId();
        ordersDetail.price = ordersDetailDto.getPrice();
        ordersDetail.amount = ordersDetailDto.getAmount();
        Menu menu = new Menu(ordersDetailDto.getMenuId());
        ordersDetail.menu = menu;
        return ordersDetail;
    }

    public static OrdersDetail createOrdersDetailFromRequestDTO(com.example.demo.dto.order.OrderDetailDTO ordersDetailDto){
        OrdersDetail ordersDetail = new OrdersDetail();
        ordersDetail.id = ordersDetailDto.getId();
        ordersDetail.price = ordersDetailDto.getPrice();
        ordersDetail.amount = ordersDetailDto.getAmount();
        Menu menu = new Menu(ordersDetailDto.getMenuId());
        ordersDetail.menu = menu;
        return ordersDetail;
    }

}
