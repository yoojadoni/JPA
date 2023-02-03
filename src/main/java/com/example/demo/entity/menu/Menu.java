package com.example.demo.entity.menu;

import com.example.demo.dto.MenuDTO;
import com.example.demo.entity.BaseTimeEntity;
import com.example.demo.entity.Shop;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column(name = "menu_name")
    @Setter
    private String menuName;
    @Setter
    private int price;

    @Column(name = "image_url")
    @Setter
    private String imageurl;

    @Column(name = "use_yn")
    @Setter
    private Boolean useyn;

    @PrePersist
    public void prePersist() {
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

    @Builder
    public Menu(Long id, Shop shopId, String menuName, int price, MenuOptionEnum option, String imageurl, Boolean useyn) {
        this.id = id;
        this.shop = shopId;
        this.menuName = menuName;
        this.price = price;
        this.imageurl = imageurl;
        this.useyn = useyn;
    }

    public static Menu createMenuFromDto(MenuDTO menuDTO){
        Menu menu = new Menu();
        menu.id = menuDTO.getId();
        menu.menuName = menuDTO.getMenuName();
        menu.shop = Shop.builder()
                .id(menuDTO.getShopId())
                .build();
        menu.price = menuDTO.getPrice();

        if(menuDTO.getImageUrl() != null && !menuDTO.getImageUrl().isEmpty())
            menu.imageurl = menuDTO.getImageUrl();

        return menu;
    }
}
