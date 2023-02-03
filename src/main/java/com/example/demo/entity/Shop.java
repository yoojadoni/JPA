package com.example.demo.entity;

import com.example.demo.entity.owner.DeliveryTypeEnum;
import com.example.demo.entity.owner.Owner;
import lombok.*;
import org.apache.kafka.common.protocol.types.Field;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "shop")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Shop extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(columnDefinition = "VARCHAR(128)")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_type")
    private DeliveryTypeEnum deliveryType;

    @Column(columnDefinition = "VARCHAR(20)")
    private String tel;

    @Column(name = "address_code", columnDefinition = "VARCHAR(255)")
    private String addressCode;

    @Column(name = "address_detail", columnDefinition = "VARCHAR(255)")
    private String addressDetail;

    @Column(name = "biz_number", columnDefinition = "VARCHAR(255)")
    private String bizNumber;

    @Lob
    @Column(columnDefinition = "VARCHAR(255)")
    private byte[] info;

    @Column(name = "min_order_price")
    private int minOrderPrice;

    @Column(name = "opening_time", columnDefinition = "VARCHAR(255)")
    private String openingTime;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'CLOSE'")
    private ShopStatusEnum status;

    @Column(name = "delete_yn")
    @ColumnDefault("false")
    private Boolean deleteyn;

    @Builder
    public Shop(Long id, String name, DeliveryTypeEnum deliveryType, String tel, String addressCode, String addressDetail, String bizNumber, byte[] info, int minOrderPrice, String openingTime, Owner owner, ShopStatusEnum status, Boolean deleteyn) {
        this.id = id;
        this.name = name;
        this.deliveryType = deliveryType;
        this.tel = tel;
        this.addressCode = addressCode;
        this.addressDetail = addressDetail;
        this.bizNumber = bizNumber;
        this.info = info;
        this.minOrderPrice = minOrderPrice;
        this.openingTime = openingTime;
        this.owner = owner;
        this.status = status;
        this.deleteyn = deleteyn;
    }

    public Shop(String name){
        this.name = name;
    }


    @PrePersist
    public void prePersist() {
    }

    public void changeShop(Shop shop){
        this.name = shop.getName();
        this.deliveryType = shop.getDeliveryType();
        this.tel = shop.getTel();
        this.addressCode = shop.getAddressCode();
        this.addressDetail = shop.getAddressDetail();
        this.bizNumber = shop.getBizNumber();
        this.info = shop.getInfo();
        this.minOrderPrice = shop.getMinOrderPrice();
        this.openingTime = shop.getOpeningTime();
        this.owner = shop.getOwner();
        this.status = shop.getStatus();
        this.deleteyn = shop.getDeleteyn();
    }

    public void changeShopName(String name){
        this.name = name;
    }
}