package com.example.demo.entity;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderDetailDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
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

    //가격
    @Setter
    private int price;
    //수량
    @Setter
    private int amount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    Menu menu;

    public OrdersDetail(int price, int amount, Menu menu){
        this.price = price;
        this.amount = amount;
        this.menu = menu;
    }

    public void changeOrders(Orders orders){
        this.orders = orders;
        orders.getOrdersDetails().add(this);
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

}
