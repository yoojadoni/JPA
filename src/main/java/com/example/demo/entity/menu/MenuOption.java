package com.example.demo.entity.menu;

import com.example.demo.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of ={"id", "name", "price", "menu", "imageUrl"})
@DynamicInsert
@DynamicUpdate
public class MenuOption extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private String name;

    private Long price;

    @Column(name = "image_url")
    private String imageUrl;

    @Builder
    public MenuOption(Long id, String name, Long price, Menu menu, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menu = menu;
        this.imageUrl = imageUrl;
    }
}
