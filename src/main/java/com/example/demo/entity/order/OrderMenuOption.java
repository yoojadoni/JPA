package com.example.demo.entity.order;

import com.example.demo.entity.BaseTimeEntity;
import com.example.demo.entity.OrdersDetail;
import com.example.demo.entity.menu.MenuOption;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "order_menu_option")
public class OrderMenuOption extends BaseTimeEntity {

    @Id
    @Column(name = "order_menu_option_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private MenuOption menuOption;

    @OneToOne
    @JoinColumn(name ="order_detail_id")
    private OrdersDetail ordersDetail;

    @Builder
    public OrderMenuOption(Long id, MenuOption menuOption, OrdersDetail ordersDetail) {
        this.id = id;
        this.menuOption = menuOption;
        this.ordersDetail = ordersDetail;
    }
}
