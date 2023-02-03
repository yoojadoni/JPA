package com.example.demo.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(columnDefinition = "VARCHAR(100)")
    private String email;

    private String provider;

    private String name;

    private String tel;

    private String status;

    private String role;

    @Column(name = "address_code", columnDefinition = "VARCHAR(25)")
    private String addressCode;

    @Column(name = "address_detail", columnDefinition = "VARCHAR(255)")
    private String addressDetail;

    @Column(name = "blacklist_flag")
    @ColumnDefault("'false'")
    private Boolean blackListFlag;

    @Column(name = "fcm_id", columnDefinition = "VARCHAR(255)")
    private String fcm;

    @Builder
    public Member(Long id, @NonNull String email, String provider, String name, String tel, String status, String addressCode, String addressDetail, Boolean blackListFlag, String role, String fcm) {
        this.id = id;
        this.email = email;
        this.provider = provider;
        this.name = name;
        this.tel = tel;
        this.status = status;
        this.addressCode = addressCode;
        this.addressDetail = addressDetail;
        this.blackListFlag = blackListFlag;
        this.role = role;
        this.fcm = fcm;
    }

    public void changeMember(Member member){
        this.id = member.getId();
        this.email = member.getEmail();
        this.provider = member.getProvider();
        this.name = member.getName();
        this.tel = member.getTel();
        this.status = member.getStatus();
        this.addressCode = member.getAddressCode();
        this.addressDetail = member.getAddressDetail();
        this.blackListFlag = member.getBlackListFlag();
        this.fcm = member.getFcm();
    }

    public void changeFcm(String fcm){
        this.fcm = fcm;
    }
    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", provider='" + provider + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", status='" + status + '\'' +
                ", addressCode='" + addressCode + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", blackListFlag=" + blackListFlag +
                '}';
    }
}
