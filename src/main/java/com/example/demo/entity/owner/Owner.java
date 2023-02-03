package com.example.demo.entity.owner;

import com.example.demo.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "owner")
@ToString(of = {"id", "password", "name", "tel"})
@DynamicInsert
@DynamicUpdate
public class Owner extends BaseTimeEntity {

    @Id
    @Column(name = "owner_id", columnDefinition = "VARCHAR(128)")
    @NonNull
    private String id;

    @Column(columnDefinition = "VARCHAR(255)")
    private String password;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(columnDefinition = "VARCHAR(255)")
    private String tel;

    @Column(name = "fcm_id", columnDefinition = "VARCHAR(255)")
    private String fcm;

    @Builder
    public Owner(String id, String password, String name, String tel, String fcm){
        this.id = id;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.fcm = fcm;
    }

    public void changeFcm(String fcm){
        this.fcm = fcm;
    }

}