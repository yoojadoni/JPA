package com.example.demo.entity;

import com.example.demo.dto.MenuDTO;
import com.example.demo.dto.ResponseMenuDTO;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static java.lang.Boolean.TRUE;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of ={"id", "menuName", "price"})
@DynamicInsert
@DynamicUpdate
public class Menu extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    /*@OneToOne(mappedBy = "menu",optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    private OrdersDetail ordersDetail;*/

    @Column(name = "menu_name")
    @Setter
    private String menuName;
    @Setter
    private int price;

//    @Column(name = "hot_cold")
//    @Setter
//    private boolean hotcold;

    @Enumerated(EnumType.STRING)
    @Setter
    private MenuOptionEnum option;

    @Column(name = "image_url")
    @Setter
    private String imageurl;

    @Column(name = "USE_YN")
    @Setter
    private Boolean useyn;

    @PrePersist
    public void prePersist() {
        this.option = this.option == null ? MenuOptionEnum.valueOf("NONE") : this.option;
        this.useyn = this.useyn == null ? TRUE : this.useyn;
    }

    public Menu(Long id){
        this.id = id;

    }
    public Menu(String menuname) {
        this.menuName = menuname;
    }

    public Menu(Long id, String menuName, int price){
        this.id = id;
        this.menuName = menuName;
        this.price = price;
    }

    public Menu(String menuName, int price){
        this.menuName = menuName;
        this.price = price;
    }

    public Menu(String menuName, int price, String imageurl){
        this.menuName = menuName;
        this.price = price;
        this.imageurl = imageurl;
    }

    public static Menu createMenuFromDto(MenuDTO menuDTO){
        Menu menu = new Menu();
        menu.id = menuDTO.getId();
        menu.menuName = menuDTO.getMenuName();
        menu.price = menuDTO.getPrice();
        menu.option = menuDTO.getOption();
        return menu;
    }
}
